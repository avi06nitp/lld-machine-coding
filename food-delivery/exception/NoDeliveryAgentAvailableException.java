package exception;

public class NoDeliveryAgentAvailableException extends FoodDeliveryException {
    public NoDeliveryAgentAvailableException(String message) {
        super(message);
    }
}
