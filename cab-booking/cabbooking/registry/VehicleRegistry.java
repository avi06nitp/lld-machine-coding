package cabbooking.registry;

import cabbooking.models.Vehicle;

import java.util.HashMap;
import java.util.Map;

public class VehicleRegistry {

    public static Map<Integer, Vehicle> vehicles = new HashMap<Integer, Vehicle>();



    public static Vehicle getVehicle(int id) {
        return vehicles.get(id);
    }

    public  static void registerVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getId(),vehicle);
    }

    public static Map<Integer, Vehicle> getVehicles(){
        return vehicles;
    }

}
