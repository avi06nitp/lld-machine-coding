package elevatorsystem.service;

import elevatorsystem.enums.ElevatorState;
import elevatorsystem.models.Elevator;

public class ElevatorMovementService {

    public void moveElevator(Elevator elevator) {
        boolean hasUp = !elevator.getUpRequests().isEmpty();
        boolean hasDown = !elevator.getDownRequests().isEmpty();

        if (!hasUp && !hasDown) {
            elevator.setState(ElevatorState.IDLE);
            return;
        }
        if (hasUp && !hasDown) {
            stepUp(elevator);
            return;
        }
        if (!hasUp) {
            stepDown(elevator);
            return;
        }
        stepTowardsNearer(elevator);
    }

    private void stepUp(Elevator elevator) {
        elevator.setState(ElevatorState.MOVING_UP);
        elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
        if (elevator.getCurrentFloor() == elevator.getUpRequests().first()) {
            elevator.removeUpRequest(elevator.getCurrentFloor());
        }
    }

    private void stepDown(Elevator elevator) {
        elevator.setState(ElevatorState.MOVING_DOWN);
        elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
        if (elevator.getCurrentFloor() == elevator.getDownRequests().last()) {
            elevator.removeDownRequest(elevator.getCurrentFloor());
        }
    }

    private void stepTowardsNearer(Elevator elevator) {
        int current = elevator.getCurrentFloor();
        int nearestUp = elevator.getUpRequests().first();
        int nearestDown = elevator.getDownRequests().last();
        if (nearestUp - current < current - nearestDown) {
            stepUp(elevator);
        } else {
            stepDown(elevator);
        }
    }
}