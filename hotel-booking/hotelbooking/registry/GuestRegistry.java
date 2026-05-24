package hotelbooking.registry;

import hotelbooking.models.Guest;

import java.util.HashMap;
import java.util.Map;

public class GuestRegistry {

    private static final Map<Long, Guest> guests = new HashMap<>();

    public static Guest getGuest(Long id) {
        return guests.get(id);
    }
    public static void addGuest(Guest guest) {
        guests.put(guest.getId(), guest);
    }
    public static void removeGuest(Guest guest) {
        guests.remove(guest.getId());
    }
    public static Map<Long, Guest> getGuests() {
        return Map.copyOf(guests);
    }
}
