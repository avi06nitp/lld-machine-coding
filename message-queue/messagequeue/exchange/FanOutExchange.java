package messagequeue.exchange;

import messagequeue.models.Binding;
import messagequeue.models.Consumer;
import messagequeue.registry.ConsumerRegistry;

import java.util.Map;

public class FanOutExchange implements QueueExchange {

    @Override
    public void push(Object message, Binding bindingKey) {
        Map<Long, Consumer> consumerMap= ConsumerRegistry.getConsumers();
        for(Map.Entry<Long, Consumer> consumer : consumerMap.entrySet()){
            consumer.getValue().add(message);
        }
    }
}
