package model;

/**
 * Doubly linked list with sentinel head and tail nodes.
 * Used internally by eviction strategies to maintain order.
 * Head.next = most recently used, tail.prev = least recently used.
 */
public class DoublyLinkedList<K, V> {
    private final CacheEntry<K, V> head;
    private final CacheEntry<K, V> tail;
    private int size;

    public DoublyLinkedList() {
        head = new CacheEntry<>(null, null);
        tail = new CacheEntry<>(null, null);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    public void addToFront(CacheEntry<K, V> entry) {
        entry.next = head.next;
        entry.prev = head;
        head.next.prev = entry;
        head.next = entry;
        size++;
    }

    public void remove(CacheEntry<K, V> entry) {
        entry.prev.next = entry.next;
        entry.next.prev = entry.prev;
        entry.prev = null;
        entry.next = null;
        size--;
    }

    public CacheEntry<K, V> removeLast() {
        if (isEmpty()) {
            return null;
        }
        CacheEntry<K, V> last = tail.prev;
        remove(last);
        return last;
    }

    public boolean isEmpty() {
        return head.next == tail;
    }

    public int size() {
        return size;
    }
}
