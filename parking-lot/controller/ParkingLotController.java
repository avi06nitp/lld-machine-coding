package controller;

import enums.VehicleType;
import exceptions.FloorNotFoundException;
import exceptions.NoAvailableSpotException;
import exceptions.VehicleNotParkedException;
import factory.VehicleFactory;
import models.Floor;
import models.ParkingSpot;
import models.Ticket;
import models.Vehicle;
import registry.FloorRegistry;
import service.ParkingService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ParkingLotController {

    private final String name;
    private final List<Floor> floorList;
    private final int noOfFloors;
    private final Scanner scanner;
    private final ParkingService parkingService;
    private final VehicleFactory vehicleFactory;
    private final FloorRegistry floorRegistry;

    public ParkingLotController(String name, int noOfFloors, Scanner scanner, ParkingService parkingService, VehicleFactory vehicleFactory, FloorRegistry floorRegistry) {
        this.name = name;
        this.noOfFloors = noOfFloors;
        this.parkingService = parkingService;
        this.vehicleFactory = vehicleFactory;
        this.floorList = new ArrayList<>();
        this.scanner = scanner;
        this.floorRegistry = floorRegistry;
    }

    public void initialize() throws InterruptedException {
        setupFloors();

        Vehicle vehicle = createVehicle();
        if (vehicle == null) return;

        parkVehicle(vehicle);
        showAvailableSpots();
        sleep(5000);
        unparkVehicle(vehicle);
        showAvailableSpots();
    }

    private void setupFloors() {
        for (int i = 1; i <= this.noOfFloors; i++) {
            Floor floor = new Floor(new ArrayList<>());
            floorList.add(floor);
            int counter = 1;
            for (VehicleType vehicleType : VehicleType.values()) {
                int capacity = 0;
                while (true) {
                    try {
                        System.out.println("\033[1;33m  ► Capacity for \033[1;36m" + vehicleType + "\033[0m\033[1;33m on Floor_" + i + ":\033[0m");
                        capacity = scanner.nextInt();
                        scanner.nextLine();
                        break;
                    } catch (InputMismatchException e) {
                        scanner.nextLine();
                        System.out.println("\033[1;31m  [✘] Invalid input — please enter a number.\033[0m");
                    }
                }
                for (int j = 0; j < capacity; j++) {
                    floor.getParkingSpots().add(new ParkingSpot(vehicleType, "PS-" + counter));
                    counter++;
                }
            }
            floorRegistry.addFloor(floor);
        }
    }

    private Vehicle createVehicle() {
        System.out.println("\033[1;33m  ► Enter the vehicle's license plate number:\033[0m");
        String licensePlate = scanner.nextLine();
        try {
            System.out.println("\033[1;33m  ► Vehicle type \033[2m[TWO_WHEELER / FOUR_WHEELER / HEAVY_VEHICLE]\033[0m\033[1;33m:\033[0m");
            VehicleType vehicleType = VehicleType.valueOf(scanner.nextLine().trim().toUpperCase());
            return vehicleFactory.createVehicle(vehicleType, licensePlate);
        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31m  [✘] Invalid vehicle type. Use: TWO_WHEELER, FOUR_WHEELER, HEAVY_VEHICLE\033[0m");
            return null;
        }
    }

    private void parkVehicle(Vehicle vehicle) {
        System.out.println("\033[1;33m  ► Select a floor to park. Available floors:\033[0m");
        for (Floor floor : floorList) {
            System.out.println("\033[1;36m    • " + floor.getFloorName() + "\033[0m");
        }
        String floorNumber = scanner.nextLine();
        try {
            Ticket ticket = parkingService.park(floorNumber, vehicle);
            System.out.println("\033[1;32m\n  [✔] Vehicle parked successfully!\033[0m");
            System.out.println("\033[1;36m  ┌─────────────── PARKING RECEIPT ───────────────┐\033[0m");
            System.out.println("\033[36m  │  Ticket #  : \033[0m" + ticket.getTicketNumber());
            System.out.println("\033[36m  │  Plate     : \033[0m" + ticket.getVehicle().getLicensePlate());
            System.out.println("\033[36m  │  Floor     : \033[0m" + ticket.getFloor().getFloorName());
            System.out.println("\033[36m  │  Spot      : \033[0m" + ticket.getParkingSpot().getSpotName());
            System.out.println("\033[36m  │  Type      : \033[0m" + ticket.getParkingSpot().getType());
            System.out.println("\033[36m  │  Entry     : \033[0m" + ticket.getEntryTime());
            System.out.println("\033[1;36m  └───────────────────────────────────────────────┘\033[0m");
        } catch (FloorNotFoundException | NoAvailableSpotException e) {
            System.out.println("\033[1;31m  [✘] " + e.getMessage() + "\033[0m");
        }
    }

    private void unparkVehicle(Vehicle vehicle) {
        try {
            Ticket ticket = parkingService.unpark(vehicle);
            System.out.println("\033[1;32m\n  [✔] Vehicle unparked successfully!\033[0m");
            System.out.println("\033[1;33m  ┌─────────────── BILLING SUMMARY ────────────────┐\033[0m");
            System.out.println("\033[33m  │  Ticket #   : \033[0m" + ticket.getTicketNumber());
            System.out.println("\033[33m  │  Total Fare : \033[1;32mRs. \033[0m" + ticket.getFare());
            System.out.println("\033[1;33m  └────────────────────────────────────────────────┘\033[0m");
        } catch (VehicleNotParkedException e) {
            System.out.println("\033[1;31m  [✘] " + e.getMessage() + "\033[0m");
        }
    }

    private void showAvailableSpots() {
        for (Floor floor : floorList) {
            System.out.println("\033[1;34m\n  ► Available spots on \033[1;36m" + floor.getFloorName() + "\033[0m\033[1;34m:\033[0m");
            for (ParkingSpot parkingSpot : floor.getParkingSpots()) {
                if (parkingSpot.getIsAvailable()) {
                    System.out.println("\033[1;32m    [✔] " + parkingSpot.getSpotName() + " (" + parkingSpot.getType() + ")\033[0m");
                }
            }
        }
    }
}