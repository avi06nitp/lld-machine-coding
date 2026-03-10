package test;

import cache.LocalCache;
import enums.EvictionPolicy;
import factory.CacheFactory;

/**
 * Unit tests for LRU eviction policy.
 * Uses simple assert-style checks without external test frameworks.
 */
public class LRUCacheTest {

    public static void main(String[] args) {
        testBasicPutAndGet();
        testEvictionEvictsLRUEntry();
        testAccessUpdatesRecency();
        testUpdateExistingKey();
        testDeleteEntry();
        testClearCache();
        testNullOnMiss();
        System.out.println("All LRU tests passed!");
    }

    private static void testBasicPutAndGet() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LRU);
        cache.put("a", 1);
        cache.put("b", 2);
        assert cache.get("a") == 1 : "Expected 1 for key 'a'";
        assert cache.get("b") == 2 : "Expected 2 for key 'b'";
        assert cache.size() == 2 : "Expected size 2";
    }

    private static void testEvictionEvictsLRUEntry() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LRU);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        // 'a' was inserted first and never accessed again — LRU candidate
        cache.put("d", 4);
        assert cache.get("a") == null : "Expected 'a' to be evicted";
        assert cache.get("d") == 4 : "Expected 'd' to be present";
        assert cache.size() == 3 : "Expected size 3";
    }

    private static void testAccessUpdatesRecency() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LRU);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        cache.get("a");  // 'a' is now MRU; 'b' becomes LRU
        cache.put("d", 4);  // 'b' should be evicted
        assert cache.get("b") == null : "Expected 'b' to be evicted after 'a' was accessed";
        assert cache.get("a") == 1 : "Expected 'a' to survive";
        assert cache.get("d") == 4 : "Expected 'd' to be present";
    }

    private static void testUpdateExistingKey() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(2, EvictionPolicy.LRU);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("a", 99);  // update existing
        assert cache.get("a") == 99 : "Expected updated value 99";
        assert cache.size() == 2 : "Expected size to stay 2 after update";
    }

    private static void testDeleteEntry() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LRU);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.delete("a");
        assert cache.get("a") == null : "Expected 'a' to be deleted";
        assert cache.size() == 1 : "Expected size 1";
    }

    private static void testClearCache() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LRU);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.clear();
        assert cache.size() == 0 : "Expected size 0 after clear";
        assert cache.get("a") == null : "Expected null after clear";
    }

    private static void testNullOnMiss() {
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LRU);
        assert cache.get("missing") == null : "Expected null for missing key";
    }
}
