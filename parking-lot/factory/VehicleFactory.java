package factory;

import enums.VehicleType;
import models.Vehicle;
import strategy.FourWheelerHourlyFairCalculationStrategy;
import strategy.HeavyVehicleHourlyFairCalculationStrategy;
import strategy.TwoWheelerHourlyFairCalculationStrategy;

public class VehicleFactory {

    public Vehicle createVehicle(VehicleType type,String licensePlate) {
        switch (type) {
            case TWO_WHEELER->{
                return new Vehicle(licensePlate, VehicleType.TWO_WHEELER, new TwoWheelerHourlyFairCalculationStrategy());
            }case FOUR_WHEELER -> {
                return new Vehicle(licensePlate, VehicleType.FOUR_WHEELER, new FourWheelerHourlyFairCalculationStrategy());
            }case HEAVY_VEHICLE -> {
                return new Vehicle(licensePlate, VehicleType.HEAVY_VEHICLE, new HeavyVehicleHourlyFairCalculationStrategy());
            }
            default -> {
                throw new IllegalArgumentException("Invalid vehicle type");
            }
        }

    }
}
