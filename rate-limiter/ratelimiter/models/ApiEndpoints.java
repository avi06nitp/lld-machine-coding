package ratelimiter.models;

import ratelimiter.strategy.RateLimitStrategy;
import ratelimiter.strategy.TokenBucketRateLimit;


public class ApiEndpoints {
    private static int idCounter = 0;
    private final int id;
    private final String endpoint;
    private final RateLimitStrategy strategy;// Window size for fixed window algorithm

    public ApiEndpoints( String endpoint, RateLimitStrategy strategy) {
        this.id = idCounter++;
        this.endpoint = endpoint;
        this.strategy = strategy;
    }


    //Getters
    public int getId() {
        return id;
    }
    public String getEndpoint() {
        return endpoint;
    }
    public RateLimitStrategy getStrategy() {
        return strategy;
    }


}
