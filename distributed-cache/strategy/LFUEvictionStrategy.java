package strategy;

import model.CacheEntry;
import model.DoublyLinkedList;

import java.util.HashMap;
import java.util.Map;

/**
 * Least Frequently Used (LFU) eviction strategy.
 *
 * Maintains:
 *   - entryMap: key -> CacheEntry (for O(1) lookup)
 *   - freqMap:  frequency -> DoublyLinkedList of entries at that frequency
 *   - minFreq:  tracks the current minimum frequency bucket
 *
 * When a tie occurs (multiple entries at minFreq), the LEAST RECENTLY USED
 * among them is evicted (LRU within the same frequency bucket).
 *
 * Time complexity: O(1) for all operations.
 */
public class LFUEvictionStrategy<K, V> implements EvictionStrategy<K, V> {
    private final int capacity;
    private int minFreq;
    private final Map<K, CacheEntry<K, V>> entryMap;
    private final Map<Integer, DoublyLinkedList<K, V>> freqMap;

    public LFUEvictionStrategy(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.entryMap = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    @Override
    public void onAccess(K key) {
        CacheEntry<K, V> entry = entryMap.get(key);
        if (entry != null) {
            promoteFrequency(entry);
        }
    }

    @Override
    public void onInsert(K key, V value) {
        CacheEntry<K, V> entry = new CacheEntry<>(key, value);
        entry.frequency = 1;
        entryMap.put(key, entry);
        freqMap.computeIfAbsent(1, k -> new DoublyLinkedList<>()).addToFront(entry);
        minFreq = 1;
    }

    @Override
    public void onRemove(K key) {
        CacheEntry<K, V> entry = entryMap.remove(key);
        if (entry != null) {
            DoublyLinkedList<K, V> bucket = freqMap.get(entry.frequency);
            if (bucket != null) {
                bucket.remove(entry);
            }
        }
    }

    @Override
    public K getEvictionCandidate() {
        DoublyLinkedList<K, V> minBucket = freqMap.get(minFreq);
        if (minBucket == null || minBucket.isEmpty()) {
            return null;
        }
        // Remove least recently used from the minimum frequency bucket
        CacheEntry<K, V> evicted = minBucket.removeLast();
        entryMap.remove(evicted.key);
        return evicted.key;
    }

    @Override
    public boolean isAtCapacity() {
        return entryMap.size() >= capacity;
    }

    private void promoteFrequency(CacheEntry<K, V> entry) {
        int oldFreq = entry.frequency;
        DoublyLinkedList<K, V> oldBucket = freqMap.get(oldFreq);
        if (oldBucket != null) {
            oldBucket.remove(entry);
        }

        // If old bucket at minFreq is now empty, increment minFreq
        if (oldFreq == minFreq && (oldBucket == null || oldBucket.isEmpty())) {
            minFreq++;
        }

        entry.frequency++;
        freqMap.computeIfAbsent(entry.frequency, k -> new DoublyLinkedList<>()).addToFront(entry);
    }
}
