package cabbooking.service;

import cabbooking.exception.VehicleNotAvailableException;
import cabbooking.models.Receipt;
import cabbooking.models.Ride;
import cabbooking.models.Rider;
import cabbooking.models.Vehicle;

public class RideService {

    private final VehicleMatchingService driverMatchingService;
    private final FareCalculationService fareCalculationService;
    private final RatingService ratingService;

    public RideService(VehicleMatchingService driverMatchingService, FareCalculationService fareCalculationStrategy, RatingService ratingService) {
        this.ratingService = ratingService;
        this.driverMatchingService = driverMatchingService;
        this.fareCalculationService=fareCalculationStrategy;
    }

    public Ride bookRide(Rider rider, double destinationLatitude, double destinationLongitude) {
       Vehicle vehicle= driverMatchingService.findVehicle(rider);
       if(vehicle==null){
           throw new VehicleNotAvailableException("No vehicle available! Please try after some time.");
       }
       vehicle.setOnline(false);
       Ride ride= Ride.createRide(vehicle, rider, destinationLatitude, destinationLongitude);
       Receipt receipt=new Receipt(ride);
       ride.setReceipt(receipt);
       rider.addRide(ride);
       vehicle.addRide(ride);
       return ride;
    }

    public Receipt completeRide(Ride ride) {
       double fare= fareCalculationService.calculateFare(ride);
       ride.getReceipt().setfare(fare);
       ride.getVehicle().setOnline(true);
       ratingService.giveRiderRating(ride);
       ratingService.giveVehicleRating(ride);
       return ride.getReceipt();
    }

}
