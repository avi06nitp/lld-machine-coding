package messagequeue.registry;

import messagequeue.models.Consumer;

import java.util.HashMap;
import java.util.Map;

public class ConsumerRegistry {

    private static final Map<Long, Consumer> consumer=new HashMap<>();

    public static Consumer getConsumer(long id) {
        return consumer.get(id);
    }
    public static void add(Consumer c) {
        consumer.put(c.getId(), c);
    }
    public static Map<Long, Consumer> getConsumers() {
        return consumer;
    }
    public static void remove(Consumer c) {
        consumer.remove(c.getId());
    }
}
