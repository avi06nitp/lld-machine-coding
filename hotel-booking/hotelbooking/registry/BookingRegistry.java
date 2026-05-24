package hotelbooking.registry;

import hotelbooking.models.Booking;

import java.util.HashMap;
import java.util.Map;

public class BookingRegistry {

   private static final Map<Long, Booking> bookings = new HashMap<>();

   public static void addBooking(Booking booking) {
       bookings.put(booking.getId(),booking);
   }
   public static void removeBooking(Booking booking) {
       bookings.remove(booking.getId());
   }
   public static Booking getBooking(Long id) {
       return bookings.get(id);
   }
   public static Map<Long, Booking> getBookings() {
       return Map.copyOf(bookings);
   }

}
