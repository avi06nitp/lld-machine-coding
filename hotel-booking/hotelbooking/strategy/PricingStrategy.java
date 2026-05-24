package hotelbooking.strategy;

import hotelbooking.models.Booking;
import hotelbooking.models.Room;

public interface PricingStrategy {
    double calculatePrice(Booking booking);
}
