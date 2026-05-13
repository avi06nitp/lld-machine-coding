package elevatorsystem.service;

import elevatorsystem.enums.RequestType;
import elevatorsystem.models.Elevator;
import elevatorsystem.models.Requests;

public class RequestElevatorService {

 private final ElevatorMatchingService elevatorMatchingService;

    public RequestElevatorService(ElevatorMatchingService elevatorMatchingService) {
        this.elevatorMatchingService = elevatorMatchingService;
    }

    public Elevator addRequest(Requests requests) {
                if (requests.getRequestType() == RequestType.CALL) {
                    Elevator elevator = elevatorMatchingService.findElevatorByName(requests.getCurrentFloor());
                    if (elevator.getCurrentFloor() > requests.getCurrentFloor()) {
                        elevator.getDownRequests().add(requests.getCurrentFloor());

                    } else {
                        elevator.getUpRequests().add(requests.getCurrentFloor());
                    }
                    return elevator;
                } else {
                    Elevator elevator = requests.getElevator();
                    if (elevator.getCurrentFloor() > requests.getTargetFloor()) {
                        elevator.getDownRequests().add(requests.getTargetFloor());
                    } else {
                        elevator.getUpRequests().add(requests.getTargetFloor());
                    }
                    return elevator;
                }
        }
}
