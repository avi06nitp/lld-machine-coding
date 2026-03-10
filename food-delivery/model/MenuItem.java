package model;

import enums.FoodCategory;

public class MenuItem {
    private final String id;
    private final String name;
    private final double price;
    private final FoodCategory category;
    private boolean available;

    public MenuItem(String id, String name, double price, FoodCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = true;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public FoodCategory getCategory() { return category; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("MenuItem{name='%s', price=%.2f, category=%s}", name, price, category);
    }
}
