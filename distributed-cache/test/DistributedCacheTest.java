package test;

import cache.DistributedCache;
import enums.EvictionPolicy;
import factory.CacheFactory;

/**
 * Unit tests for the sharded DistributedCache.
 */
public class DistributedCacheTest {

    public static void main(String[] args) {
        testBasicOperations();
        testShardingDistribution();
        testDeleteAcrossShards();
        testClearAll();
        System.out.println("All DistributedCache tests passed!");
    }

    private static void testBasicOperations() {
        DistributedCache<String, Integer> cache =
                CacheFactory.createDistributedCache(9, 3, EvictionPolicy.LRU);

        cache.put("key1", 100);
        cache.put("key2", 200);
        cache.put("key3", 300);

        assert cache.get("key1") == 100 : "Expected 100 for key1";
        assert cache.get("key2") == 200 : "Expected 200 for key2";
        assert cache.get("key3") == 300 : "Expected 300 for key3";
        assert cache.size() == 3 : "Expected size 3";
    }

    private static void testShardingDistribution() {
        DistributedCache<String, Integer> cache =
                CacheFactory.createDistributedCache(9, 3, EvictionPolicy.LFU);

        for (int i = 0; i < 9; i++) {
            cache.put("key" + i, i * 10);
        }

        assert cache.size() == 9 : "Expected total size 9, got " + cache.size();
        assert cache.getNumShards() == 3 : "Expected 3 shards";
        // All inserted keys should be retrievable
        for (int i = 0; i < 9; i++) {
            assert cache.get("key" + i) == i * 10 : "Expected " + (i * 10) + " for key" + i;
        }
    }

    private static void testDeleteAcrossShards() {
        DistributedCache<String, Integer> cache =
                CacheFactory.createDistributedCache(6, 2, EvictionPolicy.LRU);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.delete("a");
        assert !cache.containsKey("a") : "Expected 'a' to be deleted";
        assert cache.containsKey("b") : "Expected 'b' to still exist";
    }

    private static void testClearAll() {
        DistributedCache<String, Integer> cache =
                CacheFactory.createDistributedCache(6, 3, EvictionPolicy.LFU);
        cache.put("x", 1);
        cache.put("y", 2);
        cache.put("z", 3);
        cache.clear();
        assert cache.size() == 0 : "Expected size 0 after clear";
        assert cache.get("x") == null : "Expected null after clear";
    }
}
