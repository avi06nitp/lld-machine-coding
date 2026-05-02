package parkinglot.strategy;

import parkinglot.enums.VehicleType;
import parkinglot.models.Floor;
import parkinglot.models.ParkingSpot;

import java.util.Optional;

public interface LookUpStrategy {
    Optional<ParkingSpot> lookUp(Floor floor, VehicleType type);
}