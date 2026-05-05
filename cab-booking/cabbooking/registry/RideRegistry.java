package cabbooking.registry;

import cabbooking.models.Ride;

import java.util.HashMap;
import java.util.Map;

public class RideRegistry {
    public static Map<Integer, Ride> riders = new HashMap<Integer,Ride>();

    public static Ride getRider(int rideId) {
        return riders.get(rideId);
    }
    public static void registerRider(Ride ride) {
        riders.put(ride.getId(), ride);
    }
}
