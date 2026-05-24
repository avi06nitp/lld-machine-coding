package hotelbooking.strategy;

import hotelbooking.models.Booking;

import java.time.Duration;

public class SuitePricingStrategy implements PricingStrategy {

    private final double SUITE_ROOM_PRICE_PER_DAY=1200;
    @Override
    public double calculatePrice(Booking booking) {
        return SUITE_ROOM_PRICE_PER_DAY*(Duration.between(booking.getCheckinDate(), booking.getCheckoutDate()).toDays());
    }
}
