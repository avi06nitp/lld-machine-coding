import cache.Cache;
import cache.DistributedCache;
import cache.LocalCache;
import enums.EvictionPolicy;
import factory.CacheFactory;

/**
 * Demonstrates LRU and LFU eviction policies on both LocalCache and DistributedCache.
 */
public class DistributedCacheDemo {

    public static void main(String[] args) {
        demonstrateLRU();
        System.out.println();
        demonstrateLFU();
        System.out.println();
        demonstrateDistributedCache();
    }

    private static void demonstrateLRU() {
        System.out.println("=== LRU Cache Demo (capacity=3) ===");
        LocalCache<String, String> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LRU);

        cache.put("A", "Apple");
        cache.put("B", "Banana");
        cache.put("C", "Cherry");
        System.out.println("After inserting A, B, C:");
        System.out.println("  A=" + cache.get("A"));  // access A -> order: B, C, A
        System.out.println("  B=" + cache.get("B"));  // access B -> order: C, A, B

        // D goes in; C is LRU so C is evicted
        cache.put("D", "Date");
        System.out.println("After inserting D (C should be evicted):");
        System.out.println("  C=" + cache.get("C") + " (expected null)");
        System.out.println("  D=" + cache.get("D") + " (expected Date)");
        System.out.println("  Cache size=" + cache.size() + " (expected 3)");
    }

    private static void demonstrateLFU() {
        System.out.println("=== LFU Cache Demo (capacity=3) ===");
        LocalCache<String, Integer> cache = CacheFactory.createLocalCache(3, EvictionPolicy.LFU);

        cache.put("X", 10);
        cache.put("Y", 20);
        cache.put("Z", 30);

        // Access X twice, Y once — Z stays at freq=1
        cache.get("X");  // X freq=2
        cache.get("X");  // X freq=3
        cache.get("Y");  // Y freq=2

        System.out.println("After inserts + accesses (X=3, Y=2, Z=1):");
        // Z has lowest frequency — should be evicted when W is inserted
        cache.put("W", 40);
        System.out.println("After inserting W (Z should be evicted):");
        System.out.println("  Z=" + cache.get("Z") + " (expected null)");
        System.out.println("  W=" + cache.get("W") + " (expected 40)");
        System.out.println("  X=" + cache.get("X") + " (expected 10)");
        System.out.println("  Y=" + cache.get("Y") + " (expected 20)");
        System.out.println("  Cache size=" + cache.size() + " (expected 3)");
    }

    private static void demonstrateDistributedCache() {
        System.out.println("=== Distributed LRU Cache Demo (totalCapacity=9, shards=3) ===");
        DistributedCache<String, String> cache = CacheFactory.createDistributedCache(9, 3, EvictionPolicy.LRU);

        for (int i = 1; i <= 6; i++) {
            cache.put("key" + i, "value" + i);
        }

        System.out.println("Inserted 6 keys across " + cache.getNumShards() + " shards:");
        System.out.println("  Cache size=" + cache.size());

        System.out.println("  key1=" + cache.get("key1"));
        System.out.println("  key3=" + cache.get("key3"));
        System.out.println("  key6=" + cache.get("key6"));

        cache.delete("key2");
        System.out.println("After deleting key2:");
        System.out.println("  key2=" + cache.get("key2") + " (expected null)");
        System.out.println("  Cache size=" + cache.size());
    }
}
