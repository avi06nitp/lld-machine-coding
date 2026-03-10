package observer;

import model.Order;

public class UserNotificationObserver implements OrderObserver {
    @Override
    public void onOrderStatusChanged(Order order) {
        System.out.printf("[USER NOTIFICATION] Dear %s, your order #%s status: %s%n",
            order.getUser().getName(),
            order.getId().substring(0, 8),
            order.getStatus());
    }
}
