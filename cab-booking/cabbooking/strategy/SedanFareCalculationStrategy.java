package cabbooking.strategy;

import cabbooking.models.Vehicle;

public class SedanFareCalculationStrategy implements FareCalculationStrategy {
    private final double BASE_FARE=10.0;
    private final double PER_KM_FARE=5.0;

    @Override
    public double computeFare( double distance) {
        return distance*PER_KM_FARE+BASE_FARE;
    }
}
