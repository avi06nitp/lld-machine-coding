package exception;

public class OrderNotFoundException extends FoodDeliveryException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
