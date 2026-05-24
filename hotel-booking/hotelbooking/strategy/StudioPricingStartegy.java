package hotelbooking.strategy;

import hotelbooking.models.Booking;

import java.time.Duration;

public class StudioPricingStartegy implements PricingStrategy {


    private final double SUDIO_ROOM_PRICE_PER_DAY=800;

    @Override
    public double calculatePrice(Booking booking) {
        return SUDIO_ROOM_PRICE_PER_DAY*(Duration.between(booking.getCheckinDate(), booking.getCheckoutDate()).toDays());
    }
}
