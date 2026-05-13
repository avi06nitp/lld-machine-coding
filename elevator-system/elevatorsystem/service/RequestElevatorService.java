package elevatorsystem.service;

import elevatorsystem.enums.RequestType;
import elevatorsystem.models.Elevator;
import elevatorsystem.models.Request;

public class RequestElevatorService {

    private final ElevatorMatchingService elevatorMatchingService;

    public RequestElevatorService(ElevatorMatchingService elevatorMatchingService) {
        this.elevatorMatchingService = elevatorMatchingService;
    }

    public Elevator addRequest(Request request) {
        if (request.getRequestType() == RequestType.CALL) {
            return handleCall(request);
        }
        return handleSelect(request);
    }

    private Elevator handleCall(Request request) {
        Elevator elevator = elevatorMatchingService.findBestMatch(request.getCurrentFloor());
        addFloorByDirection(elevator, request.getCurrentFloor());
        return elevator;
    }

    private Elevator handleSelect(Request request) {
        Elevator elevator = request.getElevator();
        addFloorByDirection(elevator, request.getTargetFloor());
        return elevator;
    }

    private void addFloorByDirection(Elevator elevator, int floor) {
        if (elevator.getCurrentFloor() > floor) {
            elevator.addDownRequest(floor);
        } else {
            elevator.addUpRequest(floor);
        }
    }
}