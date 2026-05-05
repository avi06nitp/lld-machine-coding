package cabbooking.models;

import cabbooking.registry.RiderRegistry;

import java.util.HashMap;
import java.util.Map;

public class Rider {
    private static int idCounter = 1;
    private final int id;
    private final String name;
    private double latitude;
    private double longitude;
    private double rating;
    private Map<Integer, Ride> rides= new HashMap<>();

    private Rider(String name) {
        this.id = idCounter;
        idCounter++;
        this.name = name;
    }

    public static Rider createRider(String name) {
        Rider rider = new Rider( name);
        RiderRegistry.registerRider(rider);
        return rider;

    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getRating() {
        return rating;
    }
    public Map<Integer, Ride> getRides() {
        return rides;
    }
    public void addRide(Ride ride) {
        rides.put(ride.getId(), ride);
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return name + " (id=" + id + ")";
    }
}
