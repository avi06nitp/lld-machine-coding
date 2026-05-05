package cabbooking.models;

import cabbooking.registry.VehicleRegistry;
import cabbooking.strategy.FareCalculationStrategy;
import cabbooking.enums.VechicleType;
import cabbooking.strategy.MiniFareCalculationStrategy;
import cabbooking.strategy.SedanFareCalculationStrategy;
import cabbooking.strategy.SuvFairCalculationStrategy;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private static int nextId = 1;
    private final int id;
    private final String licensePlate;
    private final String driverName;
    private boolean isOnline;
    private double rating;
    private double latitude;
    private double longitude;
    private final VechicleType type;
    private final FareCalculationStrategy fareCalculationStrategy;
    private final List<Ride> rides = new ArrayList<>();

    private Vehicle(String driverName, String licensePlate, VechicleType type, FareCalculationStrategy fareCalculationStrategy,  double latitude, double longitude) {
        this.driverName = driverName;
        this.id = nextId;
        nextId++;
        this.licensePlate = licensePlate;
        this.type = type;
        this.fareCalculationStrategy = fareCalculationStrategy;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOnline = true;
    }


    // Factory to create Driver
    public static Vehicle createVehicle(String driver, String licensePlate, VechicleType type, double latitude, double longitude) {
        switch (type) {
            case MINI -> {
                Vehicle vehicle= new Vehicle(driver,licensePlate,type,new MiniFareCalculationStrategy(),latitude,longitude);
                VehicleRegistry.registerVehicle(vehicle);
                return vehicle;
            }case SUV -> {
                Vehicle vehicle= new Vehicle(driver,licensePlate,type,new SuvFairCalculationStrategy(),latitude,longitude);
                VehicleRegistry.registerVehicle(vehicle);
                return vehicle;
            }
            case SEDAN -> {
                Vehicle vehicle= new Vehicle(driver,licensePlate,type,new SedanFareCalculationStrategy(),latitude,longitude);
                VehicleRegistry.registerVehicle(vehicle);
                return vehicle;
            }
            default -> {
                throw new IllegalArgumentException("Invalid vehicle type");
            }
        }

    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public String getLicensePlate() {
        return licensePlate;
    }
    public String getDriver() {
        return driverName;
    }
    public VechicleType getType() {
        return type;
    }
    public FareCalculationStrategy getFareCalculationStrategy() {
        return fareCalculationStrategy;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public boolean isOnline() {
        return isOnline;
    }
    public void setOnline(boolean online) {
        isOnline = online;
    }
    public double getRating() {
        return rating;
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
    public List<Ride> getRides() {
        return rides;
    }
    public void addRide(Ride ride) {
        rides.add(ride);
    }

    @Override
    public String toString() {
        return driverName + " [" + licensePlate + ", " + type + "]";
    }
}
