package hotelbooking.service;

import hotelbooking.enums.BookingState;
import hotelbooking.enums.RoomType;
import hotelbooking.models.Booking;
import hotelbooking.models.Guest;
import hotelbooking.models.Reservation;
import hotelbooking.models.Room;
import hotelbooking.registry.RoomRegistry;


import java.time.Instant;

import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

public class BookingService {

    private final RefundService refundService;

    public BookingService(RefundService refundService) {
        this.refundService = refundService;
    }

    public Optional<Booking> createBooking(Guest guest, RoomType roomType, Instant checkinDate, Instant checkoutDate) {
        Map<Long, Room> rooms = RoomRegistry.getRooms();
        for (Room room : rooms.values()) {
            if (room.getType() == roomType) {
                synchronized (room) {
                    boolean isAvailable = true;
                    for (Reservation reservation : room.getReservations()) {
                        Instant reservedCheckin = reservation.getCheckinDate();
                        Instant reservedCheckout = reservation.getCheckoutDate();
                        if (checkinDate.isBefore(reservedCheckout) && checkoutDate.isAfter(reservedCheckin)) {
                            isAvailable = false;
                            break;
                        }
                    }
                    if (isAvailable) {
                        Reservation newReservation = new Reservation(checkinDate, checkoutDate);
                        room.addReservation(newReservation);
                        Booking booking = new Booking(guest, room, Instant.now(), checkinDate, checkoutDate);
                        return Optional.of(booking);
                    }
                }
            }

        }
        return Optional.empty();
    }

    public Booking cancelBooking(Booking booking) {
        Instant bookingCheckin = booking.getCheckinDate();
        // Free cancellation only while check-in is still more than 24 hours away.
        Instant cancellationDeadline = Instant.now().plus(24, ChronoUnit.HOURS);
        if (bookingCheckin.isAfter(cancellationDeadline) && booking.getBookingState() == BookingState.UPCOMING) {
            Room room = booking.getRoom();
            synchronized (room) {
                room.removeReservation(new Reservation(booking.getCheckinDate(), booking.getCheckoutDate()));
            }
            booking.setBookingState(BookingState.CANCELLED);
            refundService.processRefund(booking);
        }else{
            throw new IllegalStateException("Booking can't be cancelled");
        }
        return booking;
    }

}
