package hotelbooking.models;

import hotelbooking.enums.BookingState;

import java.time.Instant;

public class Booking {
    private static long idCounter = 0;
    private final long id;
    private final Guest guest;
    private final Room room;
    private final Instant bookingDate;
    private final Instant checkinDate;
    private  Instant checkoutDate;
    private BookingState bookingState;

    public Booking(Guest guest, Room room, Instant bookingDate, Instant checkinDate, Instant checkoutDate) {
        this.id = ++idCounter;
        this.guest = guest;
        this.room = room;
        this.bookingDate = bookingDate;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.bookingState=BookingState.UPCOMING;
    }
    // Getters & Setters
    public long getId() {
        return id;
    }
    public Guest getGuest() {
        return guest;
    }
    public Room getRoom() {
        return room;
    }
    public Instant getBookingDate() {
        return bookingDate;
    }
    public Instant getCheckinDate() {
        return checkinDate;
    }
    public Instant getCheckoutDate() {
        return checkoutDate;
    }
    public void setCheckoutDate(Instant checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
    public BookingState getBookingState() {
        return bookingState;
    }
    public void setBookingState(BookingState bookingState) {
        this.bookingState = bookingState;
    }
}
