import hotelbooking.enums.RoomType;
import hotelbooking.models.Booking;
import hotelbooking.models.Guest;
import hotelbooking.models.Room;
import hotelbooking.registry.BookingRegistry;
import hotelbooking.registry.GuestRegistry;
import hotelbooking.registry.RoomRegistry;
import hotelbooking.service.BookingService;
import hotelbooking.service.CheckInService;
import hotelbooking.service.PaymentService;
import hotelbooking.service.RefundService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Smoke test that drives every public piece of the hotel-booking system end to end.
 * Each step prints its result so a single run shows what works and what throws.
 */
public class HotelBooking {
    public static void main(String[] args) {
        System.out.println("=== Hotel Booking System: functional smoke test ===");

        // --- Service wiring ---
        RefundService refundService = new RefundService();
        BookingService bookingService = new BookingService(refundService);
        PaymentService paymentService = new PaymentService();
        CheckInService checkInService = new CheckInService(paymentService);

        // --- Rooms: one of each type (auto-registered in RoomRegistry) ---
        Room standard = Room.createRoom(RoomType.STANDARD, null);
        Room deluxe   = Room.createRoom(RoomType.DELUXE, null);
        Room suite    = Room.createRoom(RoomType.SUITE, null);
        Room studio   = Room.createRoom(RoomType.STUDIO, null);
        System.out.println("[Rooms]   created=" + RoomRegistry.getRooms().size()
                + " deluxeId=" + deluxe.getId());

        // --- Guests ---
        Guest guest =  Guest.createGuest( "Avinash", "9999999999", "avi@example.com");
        GuestRegistry.addGuest(guest);
        System.out.println("[Guests]  registered=" + GuestRegistry.getGuests().size()
                + " lookupById=" + GuestRegistry.getGuest(guest.getId()));

        // --- Booking ---
        Instant checkin  = Instant.now().minus(1, ChronoUnit.HOURS);
        Instant checkout = Instant.now().plus(2, ChronoUnit.DAYS);
        Optional<Booking> opt = bookingService.createBooking(guest, RoomType.DELUXE, checkin, checkout);
        if (opt.isEmpty()) {
            System.out.println("[Booking] FAILED: no deluxe room available");
            return;
        }
        Booking booking = opt.get();
        BookingRegistry.addBooking(booking);
        System.out.println("[Booking] id=" + booking.getId()
                + " state=" + booking.getBookingState()
                + " roomReservations=" + booking.getRoom().getReservations().size());

        // --- Pricing strategy ---
        try {
            double price = booking.getRoom().getPricingStrategy().calculatePrice(booking);
            System.out.println("[Pricing] amount=$" + price);
        } catch (Exception e) {
            System.out.println("[Pricing] ERROR: " + e);
        }

        // --- Overlapping booking should be rejected (only one deluxe room, clashing dates) ---
        Optional<Booking> overlap = bookingService.createBooking(guest, RoomType.DELUXE, checkin, checkout);
        System.out.println("[Overlap] secondDeluxePresent=" + overlap.isPresent() + " (expected false)");

        // --- Check-in ---
        try {
            checkInService.checkIn(booking);
            System.out.println("[CheckIn] state=" + booking.getBookingState());
        } catch (Exception e) {
            System.out.println("[CheckIn] ERROR: " + e);
        }

        // --- Check-out (removes reservation + triggers payment) ---
        try {
            checkInService.checkOut(booking);
            System.out.println("[CheckOut] state=" + booking.getBookingState()
                    + " roomReservations=" + booking.getRoom().getReservations().size());
        } catch (Exception e) {
            System.out.println("[CheckOut] ERROR: " + e);
        }

        // --- Cancellation flow (fresh UPCOMING booking on a different room) ---
        Instant cCheckin  = Instant.now().plus(2, ChronoUnit.DAYS);
        Instant cCheckout = Instant.now().plus(4, ChronoUnit.DAYS);
        Optional<Booking> cancelOpt = bookingService.createBooking(guest, RoomType.STANDARD, cCheckin, cCheckout);
        if (cancelOpt.isPresent()) {
            Booking toCancel = cancelOpt.get();
            BookingRegistry.addBooking(toCancel);
            Room stdRoom = toCancel.getRoom();
            System.out.println("[Cancel]  before state=" + toCancel.getBookingState()
                    + " roomReservations=" + stdRoom.getReservations().size());
            try {
                bookingService.cancelBooking(toCancel);
                System.out.println("[Cancel]  after  state=" + toCancel.getBookingState()
                        + " roomReservations=" + stdRoom.getReservations().size());
            } catch (Exception e) {
                System.out.println("[Cancel]  ERROR: " + e);
            }
        } else {
            System.out.println("[Cancel]  FAILED: no standard room available");
        }

        // --- Registry summary ---
        System.out.println("[Registry] bookings=" + BookingRegistry.getBookings().size()
                + " rooms=" + RoomRegistry.getRooms().size()
                + " guests=" + GuestRegistry.getGuests().size());

        System.out.println("=== smoke test complete ===");
    }
}