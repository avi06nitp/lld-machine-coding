package parkinglot.strategy;

import parkinglot.models.Floor;
import parkinglot.models.ParkingSpot;
import parkinglot.models.Ticket;
import parkinglot.models.Vehicle;

public interface HourlyFairCalculationStrategy {

    Double calculateHourlyFair(Ticket ticket);

}
