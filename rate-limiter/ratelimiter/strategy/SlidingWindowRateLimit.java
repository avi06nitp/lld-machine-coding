package ratelimiter.strategy;

import ratelimiter.models.Request;
import ratelimiter.models.User;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimit implements RateLimitStrategy{

   private final Map<User, Deque<Instant>> slidingWindowRateLimitHolderMap=new ConcurrentHashMap<>();
    private final long windowSizeinSeconds;
    private  final long limit;

    public SlidingWindowRateLimit(long windowSizeinSeconds, long limit) {
        this.windowSizeinSeconds = windowSizeinSeconds;
        this.limit = limit;
    }

    @Override
    public boolean allowRequest(Request request) {

        User user=request.getUsername();
        Queue<Instant> window=slidingWindowRateLimitHolderMap.computeIfAbsent(user, k->new ArrayDeque<>());
        synchronized (window) {
            Instant now = Instant.now();
            Instant cutOff=now.minusSeconds(windowSizeinSeconds);
            while (!window.isEmpty() && window.peek().isBefore(cutOff)) {
                window.remove();
            }
            if (window.size() < limit) {
                window.add(now);
                return true;
            }

        }

        return false;
    }

}
