package hotelbooking.registry;

import hotelbooking.models.Room;
import java.util.HashMap;
import java.util.Map;

public class RoomRegistry {
    private static final Map<Long, Room> rooms = new HashMap<>();

    public static Room getRoom(Long id) {
        return rooms.get(id);
    }
    public static void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }
    public static void removeRoom(Room room) {
        rooms.remove(room.getId());
    }
    public static Map<Long, Room> getRooms() {
        return Map.copyOf(rooms);
    }
}
