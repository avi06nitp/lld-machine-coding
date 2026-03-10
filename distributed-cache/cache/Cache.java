package cache;

/**
 * Core cache interface defining the contract for all cache implementations.
 */
public interface Cache<K, V> {
    /**
     * Stores a key-value pair in the cache. Evicts an entry if at capacity.
     */
    void put(K key, V value);

    /**
     * Retrieves the value for the given key, or null if not present.
     */
    V get(K key);

    /**
     * Removes the entry for the given key.
     */
    void delete(K key);

    /**
     * Returns true if the cache contains the given key.
     */
    boolean containsKey(K key);

    /**
     * Returns the current number of entries in the cache.
     */
    int size();

    /**
     * Removes all entries from the cache.
     */
    void clear();
}
