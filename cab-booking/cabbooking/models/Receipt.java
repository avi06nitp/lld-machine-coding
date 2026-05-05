package cabbooking.models;

public class Receipt {

    private final Vehicle vehicle;
    private final Rider rider;
    private final double fromLatitude;
    private final double fromLongitude;
    private final double toLatitude;
    private final double toLongitude;
    private double fare;

    public Receipt(Ride ride) {
        this.vehicle =ride.getVehicle();
        this.rider = ride.getRider();
        this.fromLatitude = ride.getFromlatitude();
        this.fromLongitude = ride.getFromlongitude();
        this.toLatitude = ride.getTolatitude();
        this.toLongitude = ride.getTolongitude();

    }

    public void setfare(double fare) {
        this.fare = fare;
    }
    public double getFare() {
        return fare;
    }

    public void printReceipt() {
        System.out.println("\033[1;36m");
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.println("  ║            RIDE RECEIPT              ║");
        System.out.println("  ╚══════════════════════════════════════╝\033[0m");
        System.out.println("\033[36m  Rider   : " + rider + "\033[0m");
        System.out.println("\033[36m  Driver  : " + vehicle + "\033[0m");
        System.out.println("\033[36m  From    : (" + fromLatitude + ", " + fromLongitude + ")\033[0m");
        System.out.println("\033[36m  To      : (" + toLatitude + ", " + toLongitude + ")\033[0m");
        System.out.println("\033[1;32m  Fare    : ₹" + fare + "\033[0m");
    }


}
