package messagequeue.models;

import messagequeue.enums.ExchangeType;
import messagequeue.exchange.*;

public class Binding {

    private final ExchangeType exchangeType;
    private final Object message;
    private final QueueExchange exchange;
    private final String bindingKey;

    private Binding(ExchangeType exchangeType, Object message, QueueExchange exchange, String bindingKey) {
        this.exchangeType = exchangeType;
        this.message = message;
        this.exchange = exchange;
        this.bindingKey = bindingKey;
    }

    public  static Binding createBinding(ExchangeType exchangeType, Object message, String bindingKey) {
        switch (exchangeType) {
            case FANOUT->{
                return new Binding(exchangeType,message,new FanOutExchange(), bindingKey);
            }
            case TOPIC->{
                return new Binding(exchangeType,message,new TopicExchange(), bindingKey);
            }
            case DIRECT -> {
                return new Binding(exchangeType,message,new DirectExchange(), bindingKey);
            }
//            case HEADER -> {
//                return new Binding(exchangeType,message,new HeaderExchange(), bindingKey);
//            }
            default -> {
                throw new IllegalArgumentException("Unsupported ExchangeType: " + exchangeType);
            }
        }
    }

    // Getters & Setter
    public QueueExchange getExchange() {
        return exchange;
    }
    public String getBindingKey() {
        return bindingKey;
    }
}
