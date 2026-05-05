package cabbooking.service;

import cabbooking.models.Ride;
import cabbooking.models.Rider;
import cabbooking.models.Vehicle;

import static java.lang.Math.abs;

public class FareCalculationService {



    public double calculateFare(Ride ride) {
        Vehicle vehicle = ride.getVehicle();
        Rider rider = ride.getRider();
        double vehicleLatitude=vehicle.getLatitude();
        double vehicleLongitude=vehicle.getLongitude();
        double riderLatitude=rider.getLatitude();
        double riderLongitude=rider.getLongitude();
        double destinationLatitude=ride.getTolatitude();
        double destinationLongitude=ride.getTolongitude();
        double distanceToPickup=abs(vehicleLatitude-riderLatitude)+abs(vehicleLongitude-riderLongitude);
        double distanceToDrop=abs(destinationLatitude-riderLatitude)+abs(destinationLongitude-riderLongitude);
        double totalDistance=distanceToPickup+distanceToDrop;
        return vehicle.getFareCalculationStrategy().computeFare(totalDistance);

    }
}
