package observer;

import model.Order;

public class DeliveryAgentNotificationObserver implements OrderObserver {
    @Override
    public void onOrderStatusChanged(Order order) {
        if (order.getDeliveryAgent() != null) {
            System.out.printf("[AGENT NOTIFICATION] Agent %s - Order #%s status: %s%n",
                order.getDeliveryAgent().getName(),
                order.getId().substring(0, 8),
                order.getStatus());
        }
    }
}
