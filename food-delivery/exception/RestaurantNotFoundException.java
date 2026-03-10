package exception;

public class RestaurantNotFoundException extends FoodDeliveryException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
