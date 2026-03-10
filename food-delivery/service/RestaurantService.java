package service;

import exception.RestaurantNotFoundException;
import model.MenuItem;
import model.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantService {
    private final Map<String, Restaurant> restaurants = new HashMap<>();

    public Restaurant addRestaurant(Restaurant restaurant) {
        restaurants.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    public Restaurant getRestaurant(String restaurantId) {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("Restaurant not found: " + restaurantId);
        }
        return restaurant;
    }

    public void addMenuItem(String restaurantId, MenuItem menuItem) {
        getRestaurant(restaurantId).addMenuItem(menuItem);
    }

    public List<Restaurant> getAllOpenRestaurants() {
        List<Restaurant> open = new ArrayList<>();
        for (Restaurant r : restaurants.values()) {
            if (r.isOpen()) open.add(r);
        }
        return open;
    }

    public List<Restaurant> searchByName(String query) {
        List<Restaurant> result = new ArrayList<>();
        for (Restaurant r : restaurants.values()) {
            if (r.isOpen() && r.getName().toLowerCase().contains(query.toLowerCase())) {
                result.add(r);
            }
        }
        return result;
    }
}
