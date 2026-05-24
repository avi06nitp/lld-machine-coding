package hotelbooking.strategy;

import hotelbooking.models.Booking;
import hotelbooking.models.Room;

import java.time.Duration;

public class DeluxePricingStrategy implements PricingStrategy {

    private final double DELUXE_ROOM_PRICE_PER_DAY=1000;

    @Override
    public double calculatePrice(Booking booking) {
        return DELUXE_ROOM_PRICE_PER_DAY*(Duration.between(booking.getCheckinDate(), booking.getCheckoutDate()).toDays());
    }
}
