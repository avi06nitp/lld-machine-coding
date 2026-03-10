package test;

import cache.LocalCache;
import enums.EvictionPolicy;
import factory.CacheFactory;

/**
 * Unit tests for LFU eviction policy.
 */
public class LFUCacheTest {

    public static void main(String[] args) {
        testBasicPutAndGet();
        testEvictionEvictsLFUEntry();
        testFrequencyTracking();
        testTieBreakByLRU();
        testUpdateExistingKey();
        testDeleteEntry();
        System.out.println("All LFU tests passed!");
    }

    private static void testBasicPutAndGet() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LFU);
        cache.put("a", 1);
        cache.put("b", 2);
        assert cache.get("a") == 1 : "Expected 1 for key 'a'";
        assert cache.get("b") == 2 : "Expected 2 for key 'b'";
    }

    private static void testEvictionEvictsLFUEntry() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LFU);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        cache.get("a");  // a: freq=2
        cache.get("b");  // b: freq=2
        // c stays at freq=1 — should be evicted
        cache.put("d", 4);
        assert cache.get("c") == null : "Expected 'c' to be evicted (lowest frequency)";
        assert cache.get("d") == 4 : "Expected 'd' to be present";
    }

    private static void testFrequencyTracking() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(2, EvictionPolicy.LFU);
        cache.put("a", 1);  // a: freq=1
        cache.put("b", 2);  // b: freq=1
        cache.get("a");     // a: freq=2
        cache.get("a");     // a: freq=3
        // b has freq=1, should be evicted
        cache.put("c", 3);
        assert cache.get("b") == null : "Expected 'b' to be evicted";
        assert cache.get("a") == 1 : "Expected 'a' to survive (high frequency)";
        assert cache.get("c") == 3 : "Expected 'c' to be present";
    }

    private static void testTieBreakByLRU() {
        // When two keys have same frequency, LRU among them is evicted
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LFU);
        cache.put("a", 1);  // a: freq=1, inserted first
        cache.put("b", 2);  // b: freq=1
        cache.put("c", 3);  // c: freq=1
        // a was inserted first (LRU among freq=1) — should be evicted
        cache.put("d", 4);
        assert cache.get("a") == null : "Expected 'a' to be evicted (LRU tiebreak)";
        assert cache.get("b") == 2 : "Expected 'b' to survive";
    }

    private static void testUpdateExistingKey() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(2, EvictionPolicy.LFU);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("a", 99);  // update — treated as access, increases frequency
        assert cache.get("a") == 99 : "Expected updated value 99";
        assert cache.size() == 2 : "Expected size to stay 2 after update";
    }

    private static void testDeleteEntry() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LFU);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.delete("a");
        assert cache.get("a") == null : "Expected 'a' to be deleted";
        assert cache.size() == 1 : "Expected size 1";
    }
}
