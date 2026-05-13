package elevatorsystem.registry;

import elevatorsystem.models.Elevator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ElevatorRegistry {
   private final static Map<String, Elevator> elevators=new ConcurrentHashMap<>();

    public static void addElevator(Elevator elevator){
        elevators.put(elevator.getName(), elevator);
    }
    public static Map<String, Elevator> getElevators(){
        return elevators;
    }
}
