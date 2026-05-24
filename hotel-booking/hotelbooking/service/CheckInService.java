package hotelbooking.service;

import hotelbooking.enums.BookingState;
import hotelbooking.models.Booking;
import hotelbooking.models.Reservation;
import hotelbooking.models.Room;

import java.time.Instant;

public class CheckInService {

    private final PaymentService paymentService;

    public CheckInService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void checkIn(Booking booking) {
        if(booking.getCheckinDate().isBefore(Instant.now()) && booking.getCheckoutDate().isAfter(Instant.now()) && booking.getBookingState() == BookingState.UPCOMING) {
            booking.setBookingState(BookingState.CHECKED_IN);
        }
    }
    public void checkOut(Booking booking) {
        if(booking.getBookingState() == BookingState.CHECKED_IN) {
            Room room = booking.getRoom();
            synchronized (room) {
                room.removeReservation(new Reservation(booking.getCheckinDate(), booking.getCheckoutDate()));
            }
            booking.setBookingState(BookingState.CHECKED_OUT);
            paymentService.makePayment(booking);
        }
    }
}
