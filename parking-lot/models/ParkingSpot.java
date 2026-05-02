package parkinglot.models;

import parkinglot.enums.VehicleType;

public class ParkingSpot {
    private final VehicleType type;
    private final String spotName;
    private Boolean isAvailable;

    public  ParkingSpot(VehicleType type, String spotName) {
        this.type = type;
        this.spotName = spotName;
        this.isAvailable = true;
    }

    public VehicleType getType() {
        return type;
    }
    public String getSpotName() {
        return spotName;
    }
    public Boolean getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
