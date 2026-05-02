import parkinglot.controller.ParkingLotController;
import parkinglot.enums.LookupType;
import parkinglot.factory.LookupFactory;
import parkinglot.factory.VehicleFactory;
import parkinglot.registry.FloorRegistry;
import parkinglot.service.ParkingService;
import parkinglot.strategy.LookUpStrategy;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ParkingLot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\033[1;36m");
            System.out.println("  ╔═══════════════════════════════════════════╗");
            System.out.println("  ║      PARKING LOT MANAGEMENT SYSTEM        ║");
            System.out.println("  ╚═══════════════════════════════════════════╝\033[0m");
            System.out.println("\033[1;33m  ► Enter a name for your parking lot:\033[0m");
            String name = scanner.nextLine();
            FloorRegistry floorRegistry = FloorRegistry.getInstance();

            System.out.println("\033[1;32m  [✔] Great name!\033[0m \033[1;33m ► How many floors does it have?\033[0m");
            int numberOfFloors;
            try {
                numberOfFloors = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("\033[1;31m  [✘] Invalid input — number of floors must be an integer.\033[0m");
                return;
            }

            LookupType lookupType;
            try {
                System.out.println("\033[1;33m  ► Lookup strategy \033[2m[NEAREST / FARTHEST]\033[0m\033[1;33m:\033[0m");
                lookupType = LookupType.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("\033[1;31m  [✘] Invalid strategy — use NEAREST or FARTHEST.\033[0m");
                return;
            }

            LookupFactory lookupFactory = new LookupFactory();
            LookUpStrategy lookUpStrategy = lookupFactory.createLookUpStrategy(lookupType, floorRegistry);
            ParkingService parkingService = new ParkingService(floorRegistry, lookUpStrategy);
            VehicleFactory vehicleFactory = new VehicleFactory();
            ParkingLotController parkingLot = new ParkingLotController(name, numberOfFloors, scanner, parkingService, vehicleFactory, floorRegistry);
            parkingLot.initialize();

        } catch (InterruptedException e) {
            System.out.println("\033[1;31m  [✘] Operation interrupted unexpectedly.\033[0m");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("\033[1;31m  [✘] Unexpected error: " + e.getMessage() + "\033[0m");
        } finally {
            scanner.close();
        }
    }
}