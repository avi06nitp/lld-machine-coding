package ratelimiter.strategy;

import ratelimiter.models.Bucket;
import ratelimiter.models.Request;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.lang.Math.min;

public class TokenBucketRateLimit implements RateLimitStrategy{

    private final Bucket bucket;
    public TokenBucketRateLimit(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public boolean allowRequest(Request request) {
        Bucket bucket =this.bucket;
        synchronized (bucket) {
            long elapsed = Duration.between(bucket.getLastRefillTime(), LocalDateTime.now()).getSeconds();
            long tokens_to_add=elapsed*bucket.getRequestsPerSecond();
            long token=min(bucket.getToken()+tokens_to_add,bucket.getSize());
            if(tokens_to_add>0){
                bucket.setLastRefillTime(LocalDateTime.now());
            }
            if(token>=1){
                token--;
                bucket.setToken(token);
               return true;
            }else{
               return false;
            }
        }

    }
}
