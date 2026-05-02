package strategy;

import enums.VehicleType;
import models.Floor;
import models.ParkingSpot;

import java.util.Optional;

public interface LookUpStrategy {
    Optional<ParkingSpot> lookUp(Floor floor, VehicleType type);
}