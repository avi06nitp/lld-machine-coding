package exception;

public class InvalidOrderStateException extends FoodDeliveryException {
    public InvalidOrderStateException(String message) {
        super(message);
    }
}
