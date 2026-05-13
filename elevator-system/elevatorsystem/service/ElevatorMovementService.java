package elevatorsystem.service;

import elevatorsystem.enums.ElevatorState;
import elevatorsystem.models.Elevator;


public class ElevatorMovementService {

    public void moveElevator(Elevator elevator) {

            if (elevator.getDownRequests().isEmpty() && elevator.getUpRequests().isEmpty()) {
                elevator.setState(ElevatorState.IDLE);

            } else if (elevator.getDownRequests().isEmpty() && !elevator.getUpRequests().isEmpty()) {
                elevator.setState(ElevatorState.MOVING_UP);
                elevator.setCurrentFloor(elevator.getCurrentFloor()+1);
                if(elevator.getCurrentFloor()==elevator.getUpRequests().first()){
                    elevator.getUpRequests().remove(elevator.getCurrentFloor());
                }
            }else if(elevator.getUpRequests().isEmpty() && !elevator.getDownRequests().isEmpty()) {
                elevator.setState(ElevatorState.MOVING_DOWN);
                elevator.setCurrentFloor(elevator.getCurrentFloor()-1);
                if(elevator.getCurrentFloor()==elevator.getDownRequests().last()){
                    elevator.getDownRequests().remove(elevator.getCurrentFloor());
                }
            }else if(!elevator.getDownRequests().isEmpty() && !elevator.getUpRequests().isEmpty()) {
                int currentFloor=elevator.getCurrentFloor();
                int nearestUpFloor=elevator.getUpRequests().first();
                int nearestDownFloor=elevator.getDownRequests().last();
                if(nearestUpFloor-currentFloor<currentFloor-nearestDownFloor){
                    elevator.setState(ElevatorState.MOVING_UP);
                    elevator.setCurrentFloor(elevator.getCurrentFloor()+1);
                    if(elevator.getCurrentFloor()==elevator.getUpRequests().first()){
                        elevator.getUpRequests().remove(elevator.getCurrentFloor());
                    }
                }else{
                    elevator.setState(ElevatorState.MOVING_DOWN);
                    elevator.setCurrentFloor(elevator.getCurrentFloor()-1);
                    if(elevator.getCurrentFloor()==elevator.getDownRequests().last()){
                        elevator.getDownRequests().remove(elevator.getCurrentFloor());
                    }
                }
            }
        }

}
