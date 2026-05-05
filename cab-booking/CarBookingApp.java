import cabbooking.controller.CabServiceController;
import cabbooking.service.FareCalculationService;
import cabbooking.service.RatingService;
import cabbooking.service.RideService;
import cabbooking.service.VehicleMatchingService;

import java.util.Scanner;

public class CarBookingApp {
    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        FareCalculationService fareCalculationService = new FareCalculationService();
        VehicleMatchingService vehicleMatchingService = new VehicleMatchingService();
        RatingService ratingService = new RatingService(scanner);
        RideService rideService=new RideService(vehicleMatchingService,fareCalculationService,ratingService);
        CabServiceController controller=new CabServiceController(vehicleMatchingService,rideService,ratingService,fareCalculationService,scanner);
        controller.initialize();
    }
}
