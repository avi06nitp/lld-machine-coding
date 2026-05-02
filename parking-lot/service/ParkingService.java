package service;

import enums.VehicleType;
import exceptions.FloorNotFoundException;
import exceptions.NoAvailableSpotException;
import exceptions.VehicleNotParkedException;
import models.Floor;
import models.ParkingSpot;
import models.Ticket;
import models.Vehicle;
import registry.FloorRegistry;
import strategy.LookUpStrategy;

import java.util.Optional;

public class ParkingService {

    private final FloorRegistry floorRegistry;
    private final LookUpStrategy lookUpStrategy;

    public ParkingService(FloorRegistry floorRegistry, LookUpStrategy lookUpStrategy) {
        this.floorRegistry = floorRegistry;
        this.lookUpStrategy = lookUpStrategy;
    }

    public Ticket park(String floorName, Vehicle vehicle) {
        Floor floor = floorRegistry.getFloor(floorName);
        if (floor == null) {
            throw new FloorNotFoundException(floorName);
        }
        VehicleType type = vehicle.getType();
        System.out.println("\033[2m  >> Checking " + type + " availability at " + floor.getFloorName() + "...\033[0m");
        Optional<ParkingSpot> parkingSpot = lookUpStrategy.lookUp(floor, type);
        if (!parkingSpot.isPresent()) {
            throw new NoAvailableSpotException(floorName, type);
        }
        System.out.println("\033[1;32m  [✔] Spot found: " + parkingSpot.get().getSpotName() + "\033[0m");
        Ticket ticket = new Ticket(floor, parkingSpot.get(), vehicle);
        parkingSpot.get().setIsAvailable(false);
        vehicle.setTicket(ticket);
        return ticket;
    }

    public Ticket unpark(Vehicle vehicle) {
        Ticket ticket = vehicle.getTicket();
        if (ticket == null || ticket.getExitTime() != null) {
            throw new VehicleNotParkedException(vehicle.getLicensePlate());
        }
        ticket.setExitTime();
        ticket.setFare(vehicle.getStrategy().calculateHourlyFair(ticket));
        Optional<ParkingSpot> parkingSpot = ticket.getFloor().getParkingSpotByNumber(ticket.getParkingSpot().getSpotName());
        parkingSpot.ifPresent(spot -> spot.setIsAvailable(true));
        return ticket;
    }
}