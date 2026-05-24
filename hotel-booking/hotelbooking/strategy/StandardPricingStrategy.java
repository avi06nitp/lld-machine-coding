package hotelbooking.strategy;

import hotelbooking.models.Booking;

import java.time.Duration;


public class StandardPricingStrategy implements PricingStrategy {

    private final double STANDARD_ROOM_PRICE_PER_DAY=500;

    @Override
    public double calculatePrice(Booking booking) {
        return STANDARD_ROOM_PRICE_PER_DAY*(Duration.between(booking.getCheckinDate(), booking.getCheckoutDate()).toDays());
    }
}
