package strategy;

import model.CacheEntry;
import model.DoublyLinkedList;

import java.util.HashMap;
import java.util.Map;

/**
 * Least Recently Used (LRU) eviction strategy.
 *
 * Maintains a doubly linked list where:
 *   - Head = most recently used
 *   - Tail = least recently used (eviction candidate)
 *
 * Time complexity: O(1) for all operations using HashMap + DoublyLinkedList.
 */
public class LRUEvictionStrategy<K, V> implements EvictionStrategy<K, V> {
    private final int capacity;
    private final Map<K, CacheEntry<K, V>> entryMap;
    private final DoublyLinkedList<K, V> list;

    public LRUEvictionStrategy(int capacity) {
        this.capacity = capacity;
        this.entryMap = new HashMap<>();
        this.list = new DoublyLinkedList<>();
    }

    @Override
    public void onAccess(K key) {
        CacheEntry<K, V> entry = entryMap.get(key);
        if (entry != null) {
            list.remove(entry);
            list.addToFront(entry);
        }
    }

    @Override
    public void onInsert(K key, V value) {
        CacheEntry<K, V> entry = new CacheEntry<>(key, value);
        entryMap.put(key, entry);
        list.addToFront(entry);
    }

    @Override
    public void onRemove(K key) {
        CacheEntry<K, V> entry = entryMap.remove(key);
        if (entry != null) {
            list.remove(entry);
        }
    }

    @Override
    public K getEvictionCandidate() {
        CacheEntry<K, V> last = list.removeLast();
        if (last == null) {
            return null;
        }
        entryMap.remove(last.key);
        return last.key;
    }

    @Override
    public boolean isAtCapacity() {
        return entryMap.size() >= capacity;
    }
}
