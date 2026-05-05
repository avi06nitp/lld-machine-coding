package cabbooking.models;

import cabbooking.registry.RideRegistry;

public class Ride {
    private static int idCounter = 1;
    private final int id;

    private  final String RIDE_NAME_PREFIX="RIDE-";
    private final String name;
    private final Vehicle vehicle;
    private final Rider rider;
    private final double fromlatitude;
    private final double tolatitude;
    private final double fromlongitude;
    private final double tolongitude;
    private  Receipt receipt;

    private Ride(Vehicle vehicle, Rider rider, double tolatitude, double tolongitude) {
        this.id = idCounter;
        idCounter++;
        this.name = RIDE_NAME_PREFIX+id;
        this.fromlatitude = rider.getLatitude();
        this.fromlongitude = rider.getLongitude();
        this.tolatitude = tolatitude;
        this.tolongitude = tolongitude;
        this.vehicle = vehicle;
        this.rider = rider;

    }

    public static Ride createRide(Vehicle vehicle, Rider rider,double tolatitude, double tolongitude) {
        Ride ride= new Ride(vehicle,rider,  tolatitude,  tolongitude);
        RideRegistry.registerRider(ride);
        return ride;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getFromlatitude() {
        return fromlatitude;
    }
    public double getTolatitude() {
        return tolatitude;
    }
    public double getFromlongitude() {
        return fromlongitude;
    }
    public double getTolongitude() {
        return tolongitude;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public Rider getRider() {
        return rider;
    }
    public Receipt getReceipt() {
        return receipt;
    }
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

}
