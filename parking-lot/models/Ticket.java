package models;

import java.text.DateFormat;
import java.util.Date;

public class Ticket {

    private final  String ticketNumberPrefix="TKT-"+ DateFormat.getDateInstance().format(new Date())+"-";
    private static int ticketCounter=1;
    private final String ticketNumber;
    private final Floor floor;
    private final ParkingSpot parkingSpot;
    private final Vehicle vehicle;
    private final Date entryTime;
    private Date exitTime;
    private Double fare;

    public Ticket(Floor floor, ParkingSpot parkingSpot, Vehicle vehicle) {
        this.floor = floor;
        this.parkingSpot = parkingSpot;
        this.ticketNumber = (ticketNumberPrefix+ticketCounter);
        ticketCounter++;
        this.vehicle = vehicle;
        this.entryTime = new Date();
    }

    //Getters and Setters
    public String getTicketNumber() {
        return ticketNumber;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public Date getEntryTime() {
        return entryTime;
    }
    public Date getExitTime() {
        return exitTime;
    }
    public void setExitTime() {
        this.exitTime = new Date();
    }
    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }
    public Floor getFloor() {
        return floor;
    }
    public Double getFare() {
        return fare;
    }
    public void setFare(Double fare) {
        this.fare = fare;
    }

}
