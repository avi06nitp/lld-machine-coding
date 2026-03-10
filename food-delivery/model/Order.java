package model;

import enums.OrderStatus;
import observer.OrderObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Core domain model representing a food order.
 * Implements the Observer pattern to notify interested parties on status changes.
 */
public class Order {
    private final String id;
    private final User user;
    private final Restaurant restaurant;
    private final List<OrderItem> orderItems;
    private OrderStatus status;
    private DeliveryAgent deliveryAgent;
    private final double totalAmount;
    private final List<OrderObserver> observers;

    public Order(User user, Restaurant restaurant, List<OrderItem> orderItems) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.restaurant = restaurant;
        this.orderItems = new ArrayList<>(orderItems);
        this.status = OrderStatus.PLACED;
        this.observers = new ArrayList<>();
        this.totalAmount = orderItems.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        notifyObservers();
    }

    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.onOrderStatusChanged(this);
        }
    }

    public String getId() { return id; }
    public User getUser() { return user; }
    public Restaurant getRestaurant() { return restaurant; }
    public List<OrderItem> getOrderItems() { return Collections.unmodifiableList(orderItems); }
    public OrderStatus getStatus() { return status; }
    public DeliveryAgent getDeliveryAgent() { return deliveryAgent; }
    public double getTotalAmount() { return totalAmount; }

    public void setDeliveryAgent(DeliveryAgent deliveryAgent) {
        this.deliveryAgent = deliveryAgent;
    }

    @Override
    public String toString() {
        return String.format("Order{id='%s', user='%s', restaurant='%s', status=%s, total=%.2f}",
            id.substring(0, 8), user.getName(), restaurant.getName(), status, totalAmount);
    }
}
