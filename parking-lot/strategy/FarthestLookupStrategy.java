package parkinglot.strategy;

import parkinglot.enums.VehicleType;
import parkinglot.models.Floor;
import parkinglot.models.ParkingSpot;
import parkinglot.registry.FloorRegistry;

import java.util.List;
import java.util.Optional;

public class FarthestLookupStrategy implements LookUpStrategy {

    private final FloorRegistry floorRegistry;

    public FarthestLookupStrategy(FloorRegistry floorRegistry) {
        this.floorRegistry = floorRegistry;
    }

    @Override
    public Optional<ParkingSpot> lookUp(Floor floor, VehicleType type) {
        List<ParkingSpot> parkingSpots = floorRegistry.getFloor(floor.getFloorName()).getParkingSpots();
        for (int i = parkingSpots.size() - 1; i >= 0; i--) {
            ParkingSpot parkingSpot = parkingSpots.get(i);
            if (parkingSpot.getIsAvailable() && parkingSpot.getType() == type) {
                return Optional.of(parkingSpot);
            }
        }
        return Optional.empty();
    }
}