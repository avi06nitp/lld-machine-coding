package registry;

import models.Floor;

import java.util.HashMap;

public class FloorRegistry {

    private static FloorRegistry instance;
    private final HashMap<String, Floor> floorRegistry = new HashMap<>();

    private FloorRegistry() {}

    public static FloorRegistry getInstance() {
        synchronized (FloorRegistry.class) {
            if (instance == null) {
                instance = new FloorRegistry();
            }
        }
        return instance;
    }

    public Floor getFloor(String floorName) {
        return floorRegistry.get(floorName);
    }

    public void addFloor(Floor floor) {
        floorRegistry.put(floor.getFloorName(), floor);
    }
}