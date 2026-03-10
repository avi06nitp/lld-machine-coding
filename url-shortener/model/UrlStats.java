package model;

import java.time.Instant;

public class UrlStats {
    private final String shortCode;
    private final String originalUrl;
    private final long clickCount;
    private final Instant createdAt;
    private final Instant expiresAt;
    private final boolean expired;

    public UrlStats(Url url) {
        this.shortCode = url.getShortCode();
        this.originalUrl = url.getOriginalUrl();
        this.clickCount = url.getClickCount();
        this.createdAt = url.getCreatedAt();
        this.expiresAt = url.getExpiresAt();
        this.expired = url.isExpired();
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public long getClickCount() {
        return clickCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public boolean isExpired() {
        return expired;
    }

    @Override
    public String toString() {
        String expiry = expiresAt != null ? expiresAt.toString() : "Never";
        return String.format(
                "UrlStats{\n  shortCode='%s'\n  originalUrl='%s'\n  clicks=%d\n  createdAt=%s\n  expiresAt=%s\n  expired=%b\n}",
                shortCode, originalUrl, clickCount, createdAt, expiry, expired);
    }
}
