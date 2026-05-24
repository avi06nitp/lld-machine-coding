package distributedcache.models;



public class DoublyLinkedList {
    private  DoublyLinkedList prev;
    private  DoublyLinkedList next;

    private final String key;
    private final String value;

    public DoublyLinkedList(String key, String value) {
        this.key = key;
        this.value = value;
        prev = null;
        next = null;
    }

    public DoublyLinkedList getPrev() {
        return prev;
    }
    public DoublyLinkedList getNext() {
        return next;
    }
    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
    public void setPrev(DoublyLinkedList prev) {
        this.prev=prev;
    }
    public void setNext(DoublyLinkedList next) {
        this.next=next;
    }
}