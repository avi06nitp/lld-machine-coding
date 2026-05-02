package parkinglot.strategy;

import parkinglot.models.Ticket;

import java.util.Date;


public class TwoWheelerHourlyFairCalculationStrategy implements HourlyFairCalculationStrategy {

    private final Double twoWheelerHourlyFair=10.0;
    @Override
    public Double calculateHourlyFair(Ticket ticket) {
        Date entryTime=ticket.getEntryTime();
        Date exitTime=ticket.getExitTime();

        long diffInMillis = exitTime.getTime() - entryTime.getTime();
        long diffInHours = diffInMillis / (1000 * 60 * 60);
        return twoWheelerHourlyFair * Math.max(1, diffInHours);
    }
}
