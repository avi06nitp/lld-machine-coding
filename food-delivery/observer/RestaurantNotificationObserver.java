package observer;

import model.Order;

public class RestaurantNotificationObserver implements OrderObserver {
    @Override
    public void onOrderStatusChanged(Order order) {
        System.out.printf("[RESTAURANT NOTIFICATION] %s - Order #%s status: %s%n",
            order.getRestaurant().getName(),
            order.getId().substring(0, 8),
            order.getStatus());
    }
}
