package model;

import java.time.Instant;

public class Url {
    private final String shortCode;
    private final String originalUrl;
    private final Instant createdAt;
    private final Instant expiresAt; // null means never expires
    private long clickCount;

    public Url(String shortCode, String originalUrl, Instant expiresAt) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.createdAt = Instant.now();
        this.expiresAt = expiresAt;
        this.clickCount = 0;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void incrementClickCount() {
        this.clickCount++;
    }

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }

    @Override
    public String toString() {
        return String.format("Url{shortCode='%s', originalUrl='%s', clicks=%d, expired=%b}",
                shortCode, originalUrl, clickCount, isExpired());
    }
}
