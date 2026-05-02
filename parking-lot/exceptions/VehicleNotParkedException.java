package exceptions;

public class VehicleNotParkedException extends ParkingLotException {
    public VehicleNotParkedException(String licensePlate) {
        super("Vehicle \"" + licensePlate + "\" has no active parking record.");
    }
}