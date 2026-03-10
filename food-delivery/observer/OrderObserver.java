package observer;

import model.Order;

public interface OrderObserver {
    void onOrderStatusChanged(Order order);
}
