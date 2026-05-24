package hotelbooking.service;

import hotelbooking.models.Booking;

public class PaymentService {
    public void makePayment(Booking booking) {
       Double amount=booking.getRoom().getPricingStrategy().calculatePrice(booking);
        System.out.println("Payment made for amount: $"+amount);
    }
}
