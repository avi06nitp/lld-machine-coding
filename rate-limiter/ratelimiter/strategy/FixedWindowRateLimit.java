package ratelimiter.strategy;

import ratelimiter.models.Request;
import ratelimiter.models.User;
import ratelimiter.models.WindowCounter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FixedWindowRateLimit implements RateLimitStrategy {

    private final Map<User, WindowCounter> counters = new ConcurrentHashMap<>();
    private final long windowSize;
    private final long limit;

    public FixedWindowRateLimit(int windowSize, int limit) {
        this.windowSize = windowSize;
        this.limit = limit;
    }

    @Override
    public boolean allowRequest(Request request) {
       User user=request.getUsername();
       WindowCounter windowCounter=counters.computeIfAbsent(user, k->new WindowCounter());
       synchronized (windowCounter) {
           long window=windowCounter.getWindow();
           long  curentWindow= (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)/windowSize);
           if(curentWindow>window){
               windowCounter.setWindow(curentWindow);
               windowCounter.setCount(1);
               return true;

           }else if(windowCounter.getCount()<limit){
               windowCounter.setCount(windowCounter.getCount()+1);
               return true;

           }
       }

      return false;
    }



}