package messagequeue.models;

import messagequeue.enums.ExchangeType;

public class Producer {

    private static Long idCounter=1L;
    private final Long id;
    private final String producerName;

    public Producer(Long id, String producerName) {
        this.id = id;
        this.producerName = producerName;
    }
    public Long getId() {
        return id;
    }
    public String getProducerName() {
        return producerName;
    }
    public void publish(Object message, ExchangeType exchangeType, String bindingKey) {
       Broker.getInstance().sendMessage(message, exchangeType, bindingKey);
    }
}
