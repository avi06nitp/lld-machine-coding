package ratelimiter.strategy;

import ratelimiter.models.Request;

public interface RateLimitStrategy {

    boolean allowRequest(Request request);
}
