package service;

import cache.UrlCache;
import exception.CustomAliasAlreadyExistsException;
import exception.UrlExpiredException;
import exception.UrlNotFoundException;
import model.Url;
import model.UrlStats;
import strategy.CodeGenerationStrategy;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Core service handling URL shortening and expansion.
 * Uses a CodeGenerationStrategy (injected via constructor) for short-code generation
 * and an LRU UrlCache to speed up frequently-accessed mappings.
 */
public class UrlShortenerService {
    private static final int DEFAULT_CODE_LENGTH = 6;

    private final CodeGenerationStrategy codeGenerationStrategy;
    private final UrlCache urlCache;
    // Primary store: shortCode -> Url
    private final Map<String, Url> urlStore;

    public UrlShortenerService(CodeGenerationStrategy codeGenerationStrategy, int cacheCapacity) {
        this.codeGenerationStrategy = codeGenerationStrategy;
        this.urlCache = new UrlCache(cacheCapacity);
        this.urlStore = new HashMap<>();
    }

    /**
     * Shortens a URL with no custom alias and no expiry.
     */
    public String shortenUrl(String originalUrl) {
        return shortenUrl(originalUrl, null, -1);
    }

    /**
     * Shortens a URL with an optional custom alias and optional expiry duration.
     *
     * @param originalUrl          the URL to shorten
     * @param customAlias          optional custom short code; pass null for auto-generation
     * @param expiryDurationMillis expiry in milliseconds from now; pass -1 for no expiry
     * @return the short code
     */
    public String shortenUrl(String originalUrl, String customAlias, long expiryDurationMillis) {
        validateUrl(originalUrl);

        String shortCode = resolveShortCode(originalUrl, customAlias);
        Instant expiresAt = expiryDurationMillis > 0
                ? Instant.now().plusMillis(expiryDurationMillis)
                : null;

        Url url = new Url(shortCode, originalUrl, expiresAt);
        urlStore.put(shortCode, url);
        urlCache.put(shortCode, url);

        return shortCode;
    }

    /**
     * Returns the original URL for a given short code, incrementing the click counter.
     */
    public String getOriginalUrl(String shortCode) {
        Url url = lookupUrl(shortCode);
        url.incrementClickCount();
        return url.getOriginalUrl();
    }

    /**
     * Returns analytics stats for a given short code without incrementing the click count.
     */
    public UrlStats getStats(String shortCode) {
        Url url = lookupUrl(shortCode);
        return new UrlStats(url);
    }

    /**
     * Deletes a short URL mapping.
     */
    public void deleteUrl(String shortCode) {
        if (!urlStore.containsKey(shortCode)) {
            throw new UrlNotFoundException(shortCode);
        }
        urlStore.remove(shortCode);
        urlCache.remove(shortCode);
    }

    // --- private helpers ---

    private String resolveShortCode(String originalUrl, String customAlias) {
        if (customAlias != null && !customAlias.isBlank()) {
            if (urlStore.containsKey(customAlias)) {
                throw new CustomAliasAlreadyExistsException(customAlias);
            }
            return customAlias;
        }
        // Auto-generate, retrying on collisions
        String code;
        int attempts = 0;
        do {
            code = codeGenerationStrategy.generateCode(originalUrl + attempts, DEFAULT_CODE_LENGTH);
            attempts++;
        } while (urlStore.containsKey(code) && attempts < 10);
        return code;
    }

    private Url lookupUrl(String shortCode) {
        // Check cache first
        Url url = urlCache.get(shortCode).orElseGet(() -> {
            Url stored = urlStore.get(shortCode);
            if (stored != null) {
                urlCache.put(shortCode, stored);
            }
            return stored;
        });

        if (url == null) {
            throw new UrlNotFoundException(shortCode);
        }
        if (url.isExpired()) {
            throw new UrlExpiredException(shortCode);
        }
        return url;
    }

    private void validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL cannot be null or blank");
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new IllegalArgumentException("URL must start with http:// or https://");
        }
    }
}
