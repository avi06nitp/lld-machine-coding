package service;

import enums.OrderStatus;
import exception.InvalidOrderStateException;
import exception.OrderNotFoundException;
import model.DeliveryAgent;
import model.Order;
import model.OrderItem;
import model.Restaurant;
import model.User;
import observer.OrderObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private final Map<String, Order> orders = new HashMap<>();
    private final DeliveryService deliveryService;
    private final List<OrderObserver> defaultObservers = new ArrayList<>();

    public OrderService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void addDefaultObserver(OrderObserver observer) {
        defaultObservers.add(observer);
    }

    public Order placeOrder(User user, Restaurant restaurant, List<OrderItem> items) {
        Order order = new Order(user, restaurant, items);
        defaultObservers.forEach(order::addObserver);
        orders.put(order.getId(), order);

        DeliveryAgent agent = deliveryService.assignDeliveryAgent(order);
        order.setDeliveryAgent(agent);

        System.out.printf("[ORDER] Placed order #%s, assigned to agent: %s%n",
            order.getId().substring(0, 8), agent.getName());
        notifyObserversManually(order);
        return order;
    }

    public Order getOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found: " + orderId);
        }
        return order;
    }

    public void updateOrderStatus(String orderId, OrderStatus status) {
        Order order = getOrder(orderId);
        order.updateStatus(status);
        if (status == OrderStatus.DELIVERED && order.getDeliveryAgent() != null) {
            deliveryService.completeDelivery(order.getDeliveryAgent().getId());
        }
    }

    public void cancelOrder(String orderId) {
        Order order = getOrder(orderId);
        if (order.getStatus() != OrderStatus.PLACED && order.getStatus() != OrderStatus.ACCEPTED) {
            throw new InvalidOrderStateException(
                "Cannot cancel order in status: " + order.getStatus());
        }
        order.updateStatus(OrderStatus.CANCELLED);
        if (order.getDeliveryAgent() != null) {
            deliveryService.completeDelivery(order.getDeliveryAgent().getId());
        }
    }

    // Notify observers for the initial PLACED status since it's set before observers attach
    private void notifyObserversManually(Order order) {
        order.updateStatus(OrderStatus.PLACED);
    }
}
