package strategy;

import enums.VehicleType;
import models.Floor;
import models.ParkingSpot;
import registry.FloorRegistry;

import java.util.List;
import java.util.Optional;

public class NearestLookUpStrategy implements LookUpStrategy {

    private final FloorRegistry floorRegistry;

    public NearestLookUpStrategy(FloorRegistry floorRegistry) {
        this.floorRegistry = floorRegistry;
    }

    @Override
    public Optional<ParkingSpot> lookUp(Floor floor, VehicleType type) {
        List<ParkingSpot> parkingSpots = floorRegistry.getFloor(floor.getFloorName()).getParkingSpots();
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.getIsAvailable() && parkingSpot.getType() == type) {
                return Optional.of(parkingSpot);
            }
        }
        return Optional.empty();
    }
}