package exceptions;

import enums.VehicleType;

public class NoAvailableSpotException extends ParkingLotException {
    public NoAvailableSpotException(String floorName, VehicleType type) {
        super("No available " + type + " spot on " + floorName + ".");
    }
}