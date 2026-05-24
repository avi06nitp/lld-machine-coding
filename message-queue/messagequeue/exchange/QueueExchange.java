package messagequeue.exchange;

import messagequeue.models.Binding;

public interface QueueExchange {

    void push(Object message, Binding bindingKey );

}
