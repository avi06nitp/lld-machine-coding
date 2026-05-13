package ratelimiter.strategy;

import ratelimiter.models.Request;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.sleep;

public class LeakyBucketRateLimit implements RateLimitStrategy{

    private final Queue<Request> queue = new ConcurrentLinkedQueue<Request>();
    private final Integer size ;
    private final Integer requestsPerSecond;


    public LeakyBucketRateLimit(Integer size, Integer requestsPerSecond) {
        this.size = size;
        this.requestsPerSecond = requestsPerSecond;
    }

    @Override
    public boolean allowRequest(Request request) {
        synchronized (queue) {
            if(queue.size() < size) {
                queue.add(request);
                return true;
            }
            return false;
        }

    }

    // private worker for demo
    public void processRequest() throws InterruptedException {
        try{
            while(true) {
                sleep(1000L/requestsPerSecond);
                if(!queue.isEmpty()) {
                    queue.remove();
                }
            }
        }
        catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

}
