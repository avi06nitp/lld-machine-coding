package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurant {
    private final String id;
    private final String name;
    private final String address;
    private final String cuisine;
    private final List<MenuItem> menu;
    private boolean open;

    public Restaurant(String id, String name, String address, String cuisine) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisine = cuisine;
        this.menu = new ArrayList<>();
        this.open = true;
    }

    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getCuisine() { return cuisine; }
    public List<MenuItem> getMenu() { return Collections.unmodifiableList(menu); }
    public boolean isOpen() { return open; }
    public void setOpen(boolean open) { this.open = open; }

    @Override
    public String toString() {
        return String.format("Restaurant{id='%s', name='%s', cuisine='%s'}", id, name, cuisine);
    }
}
