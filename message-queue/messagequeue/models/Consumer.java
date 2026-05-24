package messagequeue.models;

import java.util.ArrayDeque;
import java.util.Queue;

public class Consumer {

    private static Long idCounter = 1L;
    private final Long id;
    private final Queue<Object> messages=new ArrayDeque<>();
    private final String consumerName;


    public Consumer(String consumerName) {
        this.id = idCounter++;
        this.consumerName = consumerName;
    }

    // Getters and Setter
    public Long getId() {
        return id;
    }
    public String getConsumerName() {
        return consumerName;
    }
    public Queue<Object> getMessages() {
        return messages;
    }
    public Object getMessage() {
        return messages.peek();
    }
    public void add(Object object) {
        messages.add(object);
    }

    public void ack(){
        Object message = messages.peek();
        System.out.println("Acknowledged Message: " + message);
        messages.poll();
    }


}
