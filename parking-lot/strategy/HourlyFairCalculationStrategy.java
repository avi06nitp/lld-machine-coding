package strategy;

import models.Floor;
import models.ParkingSpot;
import models.Ticket;
import models.Vehicle;

public interface HourlyFairCalculationStrategy {

    Double calculateHourlyFair(Ticket ticket);

}
