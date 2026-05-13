package elevatorsystem.models;

import elevatorsystem.enums.ElevatorState;
import elevatorsystem.registry.ElevatorRegistry;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class Elevator {
    private static int idCounter = 1;
    private static final String NAME_PREFIX = "Elevator:";

    private final String name;
    private final int maxFloor;
    private final SortedSet<Integer> upRequests = new TreeSet<>();
    private final SortedSet<Integer> downRequests = new TreeSet<>();

    private ElevatorState state = ElevatorState.IDLE;
    private int currentFloor = 0;

    private Elevator(int maxFloor) {
        this.name = NAME_PREFIX + idCounter++;
        this.maxFloor = maxFloor;
    }

    public static Elevator createElevator() {
        Elevator elevator = new Elevator(Building.getInstance().getNoOfFloors());
        ElevatorRegistry.addElevator(elevator);
        return elevator;
    }

    public String getName() {
        return name;
    }

    public int getMaxFloor() {
        return maxFloor;
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
        return Collections.unmodifiableSortedSet(upRequests);
    }

    public SortedSet<Integer> getDownRequests() {
        return Collections.unmodifiableSortedSet(downRequests);
    }

    public void addUpRequest(int floor) {
        upRequests.add(floor);
    }

    public void addDownRequest(int floor) {
        downRequests.add(floor);
    }

    public void removeUpRequest(int floor) {
        upRequests.remove(floor);
    }

    public void removeDownRequest(int floor) {
        downRequests.remove(floor);
    }

    public int totalPendingRequests() {
        return upRequests.size() + downRequests.size();
    }
}