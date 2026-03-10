package factory;

import cache.DistributedCache;
import cache.LocalCache;
import enums.EvictionPolicy;
import exception.CacheException;
import strategy.EvictionStrategy;
import strategy.LFUEvictionStrategy;
import strategy.LRUEvictionStrategy;

/**
 * Factory for creating cache instances.
 * Decouples cache creation from usage, following the Factory pattern.
 */
public class CacheFactory {

    private CacheFactory() {
        // Utility class - not instantiable
    }

    /**
     * Creates an eviction strategy for the given policy and capacity.
     */
    public static <K, V> EvictionStrategy<K, V> createEvictionStrategy(EvictionPolicy policy, int capacity) {
        switch (policy) {
            case LRU:
                return new LRUEvictionStrategy<>(capacity);
            case LFU:
                return new LFUEvictionStrategy<>(capacity);
            default:
                throw new CacheException("Unsupported eviction policy: " + policy);
        }
    }

    /**
     * Creates a single-node LocalCache with the given capacity and eviction policy.
     */
    public static <K, V> LocalCache<K, V> createLocalCache(int capacity, EvictionPolicy policy) {
        EvictionStrategy<K, V> strategy = createEvictionStrategy(policy, capacity);
        return new LocalCache<>(strategy);
    }

    /**
     * Creates a DistributedCache sharded across multiple nodes.
     */
    public static <K, V> DistributedCache<K, V> createDistributedCache(
            int totalCapacity, int numShards, EvictionPolicy policy) {
        return new DistributedCache<>(totalCapacity, numShards, policy);
    }
}
