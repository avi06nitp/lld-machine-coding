package strategy;

/**
 * Strategy interface for cache eviction policies.
 * Follows the Strategy pattern - allows swapping eviction algorithms at runtime.
 */
public interface EvictionStrategy<K, V> {
    /**
     * Called when a key is accessed (get operation).
     */
    void onAccess(K key);

    /**
     * Called when a new key-value pair is inserted.
     */
    void onInsert(K key, V value);

    /**
     * Called when a key is explicitly removed.
     */
    void onRemove(K key);

    /**
     * Returns the key of the entry that should be evicted next.
     * Does NOT remove the entry - caller is responsible for removal.
     */
    K getEvictionCandidate();

    /**
     * Returns true if eviction is needed (cache is at capacity).
     */
    boolean isAtCapacity();
}
