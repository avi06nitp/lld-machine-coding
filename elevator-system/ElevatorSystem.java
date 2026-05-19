import elevatorsystem.enums.ExternalRequestDirection;
import elevatorsystem.enums.RequestType;
import elevatorsystem.models.Building;
import elevatorsystem.models.Elevator;
import elevatorsystem.models.Request;
import elevatorsystem.registry.ElevatorRegistry;
import elevatorsystem.service.ElevatorMatchingService;
import elevatorsystem.service.ElevatorMovementService;
import elevatorsystem.service.RequestElevatorService;
import elevatorsystem.strategy.NearestLoadAwareStrategy;
import elevatorsystem.strategy.SchedulingStrategy;

import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem {

    private static final SchedulingStrategy schedulingStrategy = new NearestLoadAwareStrategy();
    private static final ElevatorMatchingService matchingService = new ElevatorMatchingService(schedulingStrategy);
    private static final RequestElevatorService requestService = new RequestElevatorService(matchingService);
    private static final ElevatorMovementService movementService = new ElevatorMovementService();

    public static void main(String[] args) {
        initBuilding(10, 3);

        printStatus("Initial Status");

        System.out.println("\n=== Scenario 1: External CALLs ===");
        Elevator e1 = callExternal(5, ExternalRequestDirection.UP);
        Elevator e2 = callExternal(3, ExternalRequestDirection.DOWN);
        callExternal(8, ExternalRequestDirection.UP);

        System.out.println("\n=== Scenario 2: Internal SELECTs ===");
        selectInternal(e1, 7);
        selectInternal(e2, 1);

        System.out.println("\n=== Scenario 3: Stepping simulation ===");
        runSteps(1, 8);

        printStatus("Status after 8 steps");

        System.out.println("\n=== Scenario 4: New CALLs mid-flight ===");
        callExternal(2, ExternalRequestDirection.UP);
        callExternal(9, ExternalRequestDirection.DOWN);
        runSteps(9, 20);

        printStatus("Status after step 20");

        System.out.println("\n=== Scenario 5: Edge cases (top and bottom floors) ===");
        callExternal(0, ExternalRequestDirection.UP);
        callExternal(10, ExternalRequestDirection.DOWN);
        runSteps(21, 35);

        printStatus("End of Simulation");
    }

    private static void initBuilding(int floors, int numElevators) {
        Building building = Building.getInstance();
        building.setNoOfFloors(floors);
        List<Elevator> elevators = new ArrayList<>();
        for (int i = 0; i < numElevators; i++) {
            elevators.add(Elevator.createElevator());
        }
        building.setElevators(elevators);
        System.out.println("Building initialized: " + floors + " floors, " + numElevators + " elevators");
    }

    private static Elevator callExternal(int floor, ExternalRequestDirection direction) {
        Request req = new Request.RequestBuilder()
                .requestType(RequestType.CALL)
                .currentFloor(floor)
                .elevatorDirection(direction)
                .build();
        Elevator assigned = requestService.addRequest(req);
        System.out.println("External request: Floor " + floor + ", Direction " + direction
                + " -> Assigned to " + assigned.getName());
        return assigned;
    }

    private static void selectInternal(Elevator elevator, int targetFloor) {
        Request req = new Request.RequestBuilder()
                .requestType(RequestType.SELECT)
                .elevator(elevator)
                .targetFloor(targetFloor)
                .build();
        Elevator assigned = requestService.addRequest(req);
        System.out.println("Internal request: " + assigned.getName() + " -> Floor " + targetFloor);
    }

    private static void runSteps(int from, int to) {
        for (int step = from; step <= to; step++) {
            System.out.println("\nStep " + step + ":");
            stepAll();
        }
    }

    private static void stepAll() {
        for (Elevator elevator : ElevatorRegistry.getElevators().values()) {
            int before = elevator.getCurrentFloor();
            movementService.moveElevator(elevator);
            int after = elevator.getCurrentFloor();
            String movement = before == after
                    ? elevator.getState() + " at " + after
                    : elevator.getState() + " (" + before + " -> " + after + ")";
            System.out.println("  " + elevator.getName() + ": " + movement
                    + " | Up: " + elevator.getUpRequests()
                    + " | Down: " + elevator.getDownRequests());
        }
    }

    private static void printStatus(String header) {
        System.out.println("\n--- " + header + " ---");
        for (Elevator elevator : ElevatorRegistry.getElevators().values()) {
            System.out.println("  " + elevator.getName()
                    + ": Floor " + elevator.getCurrentFloor()
                    + " | " + elevator.getState()
                    + " | Up: " + elevator.getUpRequests()
                    + " | Down: " + elevator.getDownRequests());
        }
    }
}