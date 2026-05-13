import ratelimiter.models.ApiEndpoints;
import ratelimiter.models.Bucket;
import ratelimiter.models.Request;
import ratelimiter.models.User;
import ratelimiter.strategy.FixedWindowRateLimit;
import ratelimiter.strategy.LeakyBucketRateLimit;
import ratelimiter.strategy.SlidingWindowRateLimit;
import ratelimiter.strategy.TokenBucketRateLimit;

import static java.lang.Thread.sleep;

public class RateLimiter {

    // ANSI colors
    private static final String RESET  = "\033[0m";
    private static final String CYAN   = "\033[1;36m";
    private static final String YELLOW = "\033[1;33m";
    private static final String GREEN  = "\033[1;32m";
    private static final String RED    = "\033[1;31m";
    private static final String BLUE   = "\033[1;34m";
    private static final String DIM    = "\033[2m";

    public static void main(String[] args) throws InterruptedException {

        System.out.println(CYAN);
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║          RATE  LIMITER  DEMO                 ║");
        System.out.println("  ╚══════════════════════════════════════════════╝" + RESET);

        User user  = User.createUser("admin");
        User user2 = User.createUser("avinash");
        User user3 = User.createUser("avinash");

        // ---------- Token Bucket ----------
        section("TOKEN  BUCKET", "capacity=5, refill=2/s");
        Bucket bucket = new Bucket(5, 2);
        ApiEndpoints tbEndpoint = new ApiEndpoints("/api/orders", new TokenBucketRateLimit(bucket));
        Request tbRequest = new Request(user, tbEndpoint);

        burst("Burst of 8 requests (bucket starts full)", tbEndpoint, tbRequest, 8);
        sleep(3000);
        burst("After waiting 3s (tokens refilled, capped at 5)", tbEndpoint, tbRequest, 8);

        // ---------- Fixed Window ----------
        section("FIXED  WINDOW", "window=2s, limit=5");
        ApiEndpoints fwEndpoint = new ApiEndpoints("/api/search", new FixedWindowRateLimit(2, 5));
        Request fwRequest = new Request(user, fwEndpoint);

        burst("Burst of 8 requests in current window", fwEndpoint, fwRequest, 8);
        sleep(2500);
        burst("After waiting 2.5s (new window)", fwEndpoint, fwRequest, 8);

        // ---------- Sliding Window ----------
        section("SLIDING  WINDOW", "window=3s rolling, limit=5");
        ApiEndpoints swEndpoint = new ApiEndpoints("/api/feed", new SlidingWindowRateLimit(3, 5));
        Request swRequest = new Request(user, swEndpoint);

        burst("Burst of 8 requests at t=0", swEndpoint, swRequest, 8);
        sleep(1500);
        burst("At t=1.5s (still inside 3s window, log NOT evicted)", swEndpoint, swRequest, 3);
        sleep(2000);
        burst("At t=3.5s (original 5 timestamps evicted)", swEndpoint, swRequest, 8);

        // ---------- Leaky Bucket ----------
        section("LEAKY  BUCKET", "capacity=5, leak=2/s");
        LeakyBucketRateLimit leaky = new LeakyBucketRateLimit(5, 2);
        ApiEndpoints lbEndpoint = new ApiEndpoints("/api/leaky", leaky);
        Request lbRequest = new Request(user, lbEndpoint);

        Thread worker = new Thread(() -> {
            try {
                leaky.processRequest();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "leaky-worker");
        worker.setDaemon(true);
        worker.start();

        burst("Burst of 8 requests", lbEndpoint, lbRequest, 8);
        sleep(3000);
        burst("After 3s (worker drained ~6, bucket empty)", lbEndpoint, lbRequest, 8);

        System.out.println(CYAN + "\n  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║                 DEMO  COMPLETE               ║");
        System.out.println("  ╚══════════════════════════════════════════════╝" + RESET);
    }

    private static void section(String title, String params) {
        System.out.println();
        System.out.println(BLUE + "  ╔══════════════════════════════════════════════╗" + RESET);
        System.out.printf (BLUE + "  ║  %-42s  ║%n" + RESET, title);
        System.out.printf (BLUE + "  ║  " + DIM + "%-42s" + RESET + BLUE + "  ║%n" + RESET, params);
        System.out.println(BLUE + "  ╚══════════════════════════════════════════════╝" + RESET);
    }

    private static void burst(String label, ApiEndpoints endpoint, Request request, int count) {
        System.out.println(YELLOW + "\n  ► " + label + RESET);
        int allowed = 0;
        int rejected = 0;
        for (int i = 1; i <= count; i++) {
            boolean ok = endpoint.getStrategy().allowRequest(request);
            if (ok) allowed++; else rejected++;
            String tag = ok ? GREEN + "[✔] OK  " + RESET : RED + "[✘] FAIL" + RESET;
            System.out.printf("      Request %2d  %s%n", i, tag);
        }
        System.out.printf(DIM + "      ── %d allowed, %d rejected ──%n" + RESET, allowed, rejected);
    }
}