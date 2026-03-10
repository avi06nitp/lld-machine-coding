package controller;

import enums.OrderStatus;
import model.Cart;
import model.DeliveryAgent;
import model.MenuItem;
import model.Order;
import model.Restaurant;
import model.User;
import observer.DeliveryAgentNotificationObserver;
import observer.RestaurantNotificationObserver;
import observer.UserNotificationObserver;
import service.DeliveryService;
import service.OrderService;
import service.RestaurantService;
import strategy.NearestAgentStrategy;

import java.util.List;

public class FoodDeliveryController {
    private final RestaurantService restaurantService;
    private final OrderService orderService;
    private final DeliveryService deliveryService;

    public FoodDeliveryController() {
        this.deliveryService = new DeliveryService(new NearestAgentStrategy());
        this.orderService = new OrderService(deliveryService);
        this.restaurantService = new RestaurantService();

        orderService.addDefaultObserver(new UserNotificationObserver());
        orderService.addDefaultObserver(new RestaurantNotificationObserver());
        orderService.addDefaultObserver(new DeliveryAgentNotificationObserver());
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantService.addRestaurant(restaurant);
    }

    public void addMenuItem(String restaurantId, MenuItem menuItem) {
        restaurantService.addMenuItem(restaurantId, menuItem);
    }

    public void registerDeliveryAgent(DeliveryAgent agent) {
        deliveryService.registerAgent(agent);
    }

    public List<Restaurant> getOpenRestaurants() {
        return restaurantService.getAllOpenRestaurants();
    }

    public List<Restaurant> searchRestaurants(String query) {
        return restaurantService.searchByName(query);
    }

    public Order placeOrder(User user, Cart cart) {
        return orderService.placeOrder(user, cart.getRestaurant(), cart.getItems());
    }

    public Order placeOrder(User user, String restaurantId, List<model.OrderItem> items) {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        return orderService.placeOrder(user, restaurant, items);
    }

    public void updateOrderStatus(String orderId, OrderStatus status) {
        orderService.updateOrderStatus(orderId, status);
    }

    public void cancelOrder(String orderId) {
        orderService.cancelOrder(orderId);
    }

    public Order trackOrder(String orderId) {
        return orderService.getOrder(orderId);
    }
}
