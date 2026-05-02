package parkinglot.models;

import java.util.List;
import java.util.Optional;

public class Floor {

    private static final String FLOOR_PREFIX = "Floor_";
    private static int floorCounter = 0;
    private final String name;
    private final List<ParkingSpot> parkingSpots;

    public Floor(List<ParkingSpot> parkingSpots) {
        floorCounter++;
        this.name = FLOOR_PREFIX + floorCounter;
        this.parkingSpots = parkingSpots;
    }

    public String getFloorName() {
        return name;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public Optional<ParkingSpot> getParkingSpotByNumber(String parkingSpotNumber) {
        for (ParkingSpot parkingSpot : parkingSpots) {
            if(parkingSpot.getSpotName().equals(parkingSpotNumber)) {
                return Optional.of(parkingSpot);
            }
        }
        return Optional.empty();
    }
}
