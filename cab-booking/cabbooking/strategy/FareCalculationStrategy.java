package cabbooking.strategy;

import cabbooking.models.Vehicle;

public interface FareCalculationStrategy {

    double computeFare( double distance);
}
