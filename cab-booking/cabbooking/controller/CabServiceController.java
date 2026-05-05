package cabbooking.controller;

import cabbooking.enums.VechicleType;
import cabbooking.exception.RideNotFoundException;
import cabbooking.exception.VehicleNotAvailableException;
import cabbooking.models.Receipt;
import cabbooking.models.Ride;
import cabbooking.models.Rider;
import cabbooking.models.Vehicle;
import cabbooking.registry.RideRegistry;
import cabbooking.registry.RiderRegistry;
import cabbooking.registry.VehicleRegistry;
import cabbooking.service.FareCalculationService;
import cabbooking.service.RatingService;
import cabbooking.service.RideService;
import cabbooking.service.VehicleMatchingService;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class CabServiceController {

    private final Scanner scanner;
    private final RideService rideService;
    private final RatingService ratingService;
    private final FareCalculationService fareCalculationService;

    public CabServiceController(VehicleMatchingService driverMatchingService, RideService rideService, RatingService ratingService, FareCalculationService fareCalculationService, Scanner scanner) {
        this.rideService = rideService;
        this.ratingService = ratingService;
        this.fareCalculationService = fareCalculationService;
        this.scanner = scanner;
    }

    public void initialize() throws InterruptedException {
        onBoardVehicle();
        onBoardRider();
        sleep(5000);
        bookRide();
        sleep(15000);
        Receipt receipt = finishRide();
        receipt.printReceipt();
    }

    private Vehicle onBoardVehicle() {
        System.out.println("\033[1;34m\n  ── Onboard Vehicle ──\033[0m");
        while (true) {
            try {
                System.out.println("\033[1;33m  ► Enter driver's name:\033[0m");
                String driverName = scanner.nextLine();
                System.out.println("\033[1;33m  ► Enter license plate:\033[0m");
                String licensePlate = scanner.nextLine();
                System.out.println("\033[1;33m  ► Enter vehicle type \033[2m[MINI / SEDAN / SUV]\033[0m\033[1;33m:\033[0m");
                VechicleType type = VechicleType.valueOf(scanner.nextLine().trim().toUpperCase());
                System.out.println("\033[1;33m  ► Enter latitude:\033[0m");
                double latitude = Double.parseDouble(scanner.nextLine());
                System.out.println("\033[1;33m  ► Enter longitude:\033[0m");
                double longitude = Double.parseDouble(scanner.nextLine());
                Vehicle vehicle = Vehicle.createVehicle(driverName, licensePlate, type, latitude, longitude);
                System.out.println("\033[1;32m  [+] Vehicle onboarded — id: " + vehicle.getId() + ", plate: " + vehicle.getLicensePlate() + "\033[0m");
                return vehicle;
            } catch (IllegalArgumentException e) {
                System.out.println("\033[1;31m  [x] Invalid vehicle type — use MINI, SEDAN, or SUV.\033[0m");
            }
        }
    }

    private Rider onBoardRider() {
        System.out.println("\033[1;34m\n  ── Onboard Rider ──\033[0m");
        System.out.println("\033[1;33m  ► Enter rider's name:\033[0m");
        String riderName = scanner.nextLine();
        System.out.println("\033[1;33m  ► Enter rider's longitude:\033[0m");
        double longitude = Double.parseDouble(scanner.nextLine());
        System.out.println("\033[1;33m  ► Enter rider's latitude:\033[0m");
        double latitude = Double.parseDouble(scanner.nextLine());
        Rider rider = Rider.createRider(riderName);
        rider.setLongitude(longitude);
        rider.setLatitude(latitude);
        System.out.println("\033[1;32m  [+] Rider created — id: " + rider.getId() + ", name: " + rider.getName() + "\033[0m");
        return rider;
    }

    private Ride bookRide() {
        System.out.println("\033[1;34m\n  ── Book Ride ──\033[0m");
        while (true) {
            try {
                System.out.println("\033[1;33m  ► Enter rider's id:\033[0m");
                int riderId = Integer.parseInt(scanner.nextLine().trim());
                Rider rider = RiderRegistry.riders.get(riderId);
                if (rider == null) throw new RideNotFoundException("Rider with id " + riderId + " not found.");
                System.out.println("\033[1;33m  ► Enter destination longitude:\033[0m");
                double longitude = Double.parseDouble(scanner.nextLine());
                System.out.println("\033[1;33m  ► Enter destination latitude:\033[0m");
                double latitude = Double.parseDouble(scanner.nextLine());
                Ride ride = rideService.bookRide(rider, latitude, longitude);
                System.out.println("\033[1;32m  [+] Ride booked — id: " + ride.getId() + ", name: " + ride.getName() + "\033[0m");
                return ride;
            } catch (VehicleNotAvailableException e) {
                System.out.println("\033[1;31m  [x] " + e.getMessage() + "\033[0m");
                return null;
            } catch (RideNotFoundException e) {
                System.out.println("\033[1;31m  [x] " + e.getMessage() + "\033[0m");
            } catch (NumberFormatException e) {
                System.out.println("\033[1;31m  [x] Invalid input — please enter numbers only.\033[0m");
            }
        }
    }

    private Receipt finishRide() {
        System.out.println("\033[1;34m\n  ── Complete Ride ──\033[0m");
        while (true) {
            try {
                System.out.println("\033[1;33m  ► Enter ride id:\033[0m");
                int rideId = Integer.parseInt(scanner.nextLine().trim());
                Ride ride = RideRegistry.riders.get(rideId);
                if (ride == null) throw new RideNotFoundException("Ride with id " + rideId + " not found.");
                return rideService.completeRide(ride);
            } catch (RideNotFoundException e) {
                System.out.println("\033[1;31m  [x] " + e.getMessage() + "\033[0m");
            } catch (NumberFormatException e) {
                System.out.println("\033[1;31m  [x] Invalid input — please enter a number.\033[0m");
            }
        }
    }
}