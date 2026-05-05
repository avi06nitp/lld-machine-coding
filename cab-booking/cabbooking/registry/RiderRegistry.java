package cabbooking.registry;

import cabbooking.models.Rider;

import java.util.HashMap;
import java.util.Map;

public class RiderRegistry {
    public static Map<Integer, Rider> riders = new HashMap<Integer,Rider>();

    public Rider getRider(int riderId) {
        return riders.get(riderId);
    }
    public static void registerRider(Rider rider) {
        riders.put(rider.getId(), rider);
    }
}
