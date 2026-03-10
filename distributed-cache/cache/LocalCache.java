package cache;

import exception.CacheException;
import strategy.EvictionStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Thread-safe single-node cache backed by a pluggable eviction strategy.
 * Uses ReadWriteLock to allow concurrent reads while serializing writes.
 *
 * Follows the Strategy pattern — eviction algorithm is injected via constructor.
 */
public class LocalCache<K, V> implements Cache<K, V> {
    private final Map<K, V> store;
    private final EvictionStrategy<K, V> evictionStrategy;
    private final ReadWriteLock lock;

    public LocalCache(EvictionStrategy<K, V> evictionStrategy) {
        this.store = new HashMap<>();
        this.evictionStrategy = evictionStrategy;
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            if (store.containsKey(key)) {
                store.put(key, value);
                evictionStrategy.onAccess(key);
                return;
            }

            if (evictionStrategy.isAtCapacity()) {
                K evictedKey = evictionStrategy.getEvictionCandidate();
                if (evictedKey != null) {
                    store.remove(evictedKey);
                }
            }

            store.put(key, value);
            evictionStrategy.onInsert(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public V get(K key) {
        lock.readLock().lock();
        try {
            V value = store.get(key);
            if (value != null) {
                // Upgrade to write lock to update access order
                lock.readLock().unlock();
                lock.writeLock().lock();
                try {
                    evictionStrategy.onAccess(key);
                    return store.get(key);
                } finally {
                    lock.readLock().lock();
                    lock.writeLock().unlock();
                }
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void delete(K key) {
        lock.writeLock().lock();
        try {
            store.remove(key);
            evictionStrategy.onRemove(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean containsKey(K key) {
        lock.readLock().lock();
        try {
            return store.containsKey(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public int size() {
        lock.readLock().lock();
        try {
            return store.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void clear() {
        lock.writeLock().lock();
        try {
            store.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
