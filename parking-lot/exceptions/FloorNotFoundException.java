package parkinglot.exceptions;

public class FloorNotFoundException extends ParkingLotException {
    public FloorNotFoundException(String floorName) {
        super("Floor \"" + floorName + "\" does not exist.");
    }
}