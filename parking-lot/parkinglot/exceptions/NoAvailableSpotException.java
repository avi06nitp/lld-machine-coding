package parkinglot.exceptions;

import parkinglot.enums.VehicleType;

public class NoAvailableSpotException extends ParkingLotException {
    public NoAvailableSpotException(String floorName, VehicleType type) {
        super("No available " + type + " spot on " + floorName + ".");
    }
}