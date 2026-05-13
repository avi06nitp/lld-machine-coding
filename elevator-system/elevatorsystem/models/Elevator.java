package elevatorsystem.models;

import elevatorsystem.enums.ElevatorState;
import elevatorsystem.registry.ElevatorRegistry;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class Elevator {
    private static int idCounter=1;
    private final String NAME_PREFIX = "Elevator:";
    private final String name;
    private ElevatorState state=ElevatorState.IDLE;
    private int currentFloor=0;
    private SortedSet<Integer> upRequests;
    private SortedSet<Integer> downRequests;
    private final int maxFloor;

    private Elevator(int maxFloor) {
        this.name = NAME_PREFIX+idCounter;
        idCounter++;
        this.maxFloor=maxFloor;
        this.downRequests=new TreeSet<>();
        this.upRequests=new TreeSet<>();
    }

    // Elevator Factory
    public static Elevator createElevator(){
        int maxNoOfFloors= Building.getInstance().getNoOfFloors();
        Elevator elevator= new Elevator(maxNoOfFloors);
        ElevatorRegistry.addElevator(elevator);
        return elevator;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }
    public ElevatorState getState() {
        return state;
    }
    public void setState(ElevatorState state) {
        this.state = state;
    }
    public int getCurrentFloor() {
        return currentFloor;
    }
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
    public SortedSet<Integer> getUpRequests() {
        return upRequests;
    }

    public SortedSet<Integer> getDownRequests() {
        return downRequests;
    }
    public int getMaxFloor() {
        return maxFloor;
    }

}
