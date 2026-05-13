package ratelimiter.models;

import java.time.LocalDateTime;

public class Bucket {
    private LocalDateTime lastRefillTime= LocalDateTime.now();
    private long token;
    private final int size;
    private final int requestsPerSecond;

    public Bucket(int size, int requestsPerSecond) {
        this.size = size;
        this.requestsPerSecond = requestsPerSecond;
        this.token = size;
    }
    // Getters & Setters
    public LocalDateTime getLastRefillTime() {
        return lastRefillTime;
    }
    public long getToken() {
        return token;
    }
    public int getSize() {
        return size;
    }
    public int getRequestsPerSecond() {
        return requestsPerSecond;
    }
    public void setLastRefillTime(LocalDateTime lastRefillTime) {
        this.lastRefillTime = lastRefillTime;
    }
    public void setToken(long token) {
        this.token = token;
    }


}
