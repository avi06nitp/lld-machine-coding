package keyvaluestore.models;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private final DoublyLinkedList head;
    private final DoublyLinkedList tail;
    private final int capacity;
    private Map<String,DoublyLinkedList> cache=new HashMap<>();

    // initialize cache
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.head=new DoublyLinkedList("head",null);
        this.tail=new DoublyLinkedList("tail",null);
        head.setNext(tail);
        tail.setPrev(head);
    }

    // Add to cache
    public void put(String key, String value) {
        // in-place update: if the key already exists, drop its old node first so we
        // don't evict an innocent key or leave a duplicate node orphaned in the list.
        // The fresh value is re-inserted at the head by the code below.
        if (cache.containsKey(key)) {
            DoublyLinkedList existing = cache.get(key);
            DoublyLinkedList prev = existing.getPrev();
            prev.setNext(existing.getNext());
            existing.getNext().setPrev(prev);
            existing.setPrev(null);
            existing.setNext(null);
            cache.remove(key);
        }

        if(cache.size()==capacity) {
            // evict the least-recently-used node: the one just before the tail sentinel
            DoublyLinkedList evicted = tail.getPrev();
            DoublyLinkedList prev = evicted.getPrev();

            // remove it from the map by its own key
            cache.remove(evicted.getKey());

            // close the gap
            prev.setNext(tail);
            tail.setPrev(prev);

            // fully detach the evicted node
            evicted.setPrev(null);
            evicted.setNext(null);
        }
        DoublyLinkedList newHead= new DoublyLinkedList(key,value);
        newHead.setNext(head.getNext());
        newHead.getNext().setPrev(newHead);
        head.setNext(newHead);
        newHead.setPrev(head);
        cache.put(key,newHead);
    }

    public String get(String key) {
        DoublyLinkedList promoted = cache.get(key);
        if (promoted == null) {
            // cache miss: HashMap.get returns null for an absent key
            return null;
        }

        // refresh as most-recently-used: unlink the node from its current position...
        DoublyLinkedList prev = promoted.getPrev();
        prev.setNext(promoted.getNext());
        promoted.getNext().setPrev(prev);
        promoted.setPrev(null);
        promoted.setNext(null);
        cache.remove(key);

        // ...and re-insert it at the head
        put(key, promoted.getValue());

        return promoted.getValue();
    }
}
