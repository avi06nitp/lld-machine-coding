package cabbooking.service;

import cabbooking.models.Rider;
import cabbooking.models.Vehicle;
import cabbooking.registry.VehicleRegistry;

import java.util.Map;

import static java.lang.Math.abs;

public class VehicleMatchingService {

    public Vehicle findVehicle(Rider rider) {
        double distance = Double.MAX_VALUE;
        Vehicle vehicleFinal = null;

        Map<Integer,Vehicle> vehicles = VehicleRegistry.getVehicles();


        for (Map.Entry<Integer, Vehicle> entry : vehicles.entrySet()) {
            Vehicle vehicle = entry.getValue();
            double vehicleLatitude=entry.getValue().getLatitude();
            double vehicleLongitude=entry.getValue().getLongitude();
            double riderLatitude=rider.getLatitude();
            double riderLongitude=rider.getLongitude();
           if(distance>abs(vehicleLongitude-riderLongitude)+abs(vehicleLatitude-riderLatitude) && vehicle.isOnline()) {
               vehicleFinal=vehicle;
               distance=abs(vehicleLongitude-riderLongitude)+abs(vehicleLatitude-riderLatitude);
           }
        }
        return vehicleFinal;

    }
}
