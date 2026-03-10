package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private final User user;
    private Restaurant restaurant;
    private final List<OrderItem> items;

    public Cart(User user) {
        this.user = user;
        this.items = new ArrayList<>();
    }

    public void setRestaurant(Restaurant restaurant) {
        if (this.restaurant != null && !this.restaurant.getId().equals(restaurant.getId())) {
            items.clear();
        }
        this.restaurant = restaurant;
    }

    public void addItem(MenuItem menuItem, int quantity) {
        items.add(new OrderItem(menuItem, quantity));
    }

    public void clear() {
        items.clear();
        restaurant = null;
    }

    public double getTotalAmount() {
        return items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public User getUser() { return user; }
    public Restaurant getRestaurant() { return restaurant; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
}
