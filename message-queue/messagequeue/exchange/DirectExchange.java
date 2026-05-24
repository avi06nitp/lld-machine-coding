package messagequeue.exchange;

import messagequeue.models.Binding;
import messagequeue.models.Consumer;
import messagequeue.registry.ConsumerRegistry;

import java.util.Map;

public class DirectExchange  implements QueueExchange{

    @Override
    public void push(Object message, Binding bindingKey) {
        Map<Long, Consumer> consumerMap= ConsumerRegistry.getConsumers();
        for(Map.Entry<Long, Consumer> consumer : consumerMap.entrySet()){
            if(consumer.getValue().getConsumerName().equals(bindingKey.getBindingKey())){
                consumer.getValue().add(message);
            }
        }
    }
}
