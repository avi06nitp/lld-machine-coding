package model;

/**
 * Doubly linked list node used by LRU and LFU eviction strategies.
 * Stores key-value pair with frequency tracking for LFU.
 */
public class CacheEntry<K, V> {
    public K key;
    public V value;
    public int frequency;
    public CacheEntry<K, V> prev;
    public CacheEntry<K, V> next;

    public CacheEntry(K key, V value) {
        this.key = key;
        this.value = value;
        this.frequency = 1;
    }
}
