package parkinglot.models;

import parkinglot.enums.VehicleType;
import parkinglot.strategy.HourlyFairCalculationStrategy;

public class Vehicle {
    private final String LicensePlate;
    private final VehicleType type;
    private final HourlyFairCalculationStrategy strategy;
    private Ticket ticket;

    public Vehicle(String licensePlate, VehicleType type, HourlyFairCalculationStrategy strategy) {
        LicensePlate = licensePlate;
        this.type = type;
        this.strategy = strategy;

    }

    public String getLicensePlate() {
        return LicensePlate;
    }
    public VehicleType getType() {
        return type;
    }
    public HourlyFairCalculationStrategy getStrategy() {
        return strategy;
    }
    public Ticket getTicket() {
        return ticket;
    }
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
