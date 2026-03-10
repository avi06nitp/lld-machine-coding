package cache;

import enums.EvictionPolicy;
import factory.CacheFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Distributed cache that shards data across multiple LocalCache nodes.
 *
 * Uses consistent hashing (via modulo on key's hashCode) to distribute keys
 * evenly across shards. Each shard is an independent LocalCache with its own
 * eviction strategy instance — this prevents cross-shard lock contention.
 *
 * Design decisions:
 *   - Each shard gets capacity/numShards slots (floor division)
 *   - All shards use the same eviction policy for uniform behavior
 *   - Sharding is transparent to callers
 */
public class DistributedCache<K, V> implements Cache<K, V> {
    private final List<LocalCache<K, V>> shards;
    private final int numShards;

    public DistributedCache(int totalCapacity, int numShards, EvictionPolicy policy) {
        if (numShards <= 0) {
            throw new IllegalArgumentException("Number of shards must be positive");
        }
        if (totalCapacity < numShards) {
            throw new IllegalArgumentException("Total capacity must be >= number of shards");
        }

        this.numShards = numShards;
        this.shards = new ArrayList<>(numShards);

        int shardCapacity = totalCapacity / numShards;
        for (int i = 0; i < numShards; i++) {
            shards.add(CacheFactory.createLocalCache(shardCapacity, policy));
        }
    }

    private int getShardIndex(K key) {
        // Use absolute value to handle negative hashCodes
        return Math.abs(key.hashCode() % numShards);
    }

    private LocalCache<K, V> getShard(K key) {
        return shards.get(getShardIndex(key));
    }

    @Override
    public void put(K key, V value) {
        getShard(key).put(key, value);
    }

    @Override
    public V get(K key) {
        return getShard(key).get(key);
    }

    @Override
    public void delete(K key) {
        getShard(key).delete(key);
    }

    @Override
    public boolean containsKey(K key) {
        return getShard(key).containsKey(key);
    }

    @Override
    public int size() {
        return shards.stream().mapToInt(LocalCache::size).sum();
    }

    @Override
    public void clear() {
        shards.forEach(LocalCache::clear);
    }

    public int getNumShards() {
        return numShards;
    }
}
