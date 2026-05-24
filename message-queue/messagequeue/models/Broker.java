package messagequeue.models;

import messagequeue.enums.ExchangeType;
import messagequeue.exchange.QueueExchange;

public class Broker {

    private static Broker instance=null;

    public static Broker getInstance() {
        if(instance==null) {
            synchronized (Broker.class) {
                if(instance==null) {
                    instance = new Broker();
                }
            }
        }
        return instance;
    }

    public void sendMessage(Object message, ExchangeType exchangeType, String bindingKey) {
       Binding binding= Binding.createBinding(exchangeType, message, bindingKey);
        QueueExchange exchange= binding.getExchange();
        exchange.push(message, binding);
    }
}
