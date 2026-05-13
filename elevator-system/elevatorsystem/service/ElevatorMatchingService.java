package elevatorsystem.service;

import elevatorsystem.enums.ElevatorState;
import elevatorsystem.models.Elevator;
import elevatorsystem.registry.ElevatorRegistry;

import java.util.Map;

import static java.lang.Math.abs;

public class ElevatorMatchingService {


    public Elevator findElevatorByName(int requestedFloor) {
        Map<String, Elevator> elevatorMap= ElevatorRegistry.getElevators();
        int distance=Integer.MAX_VALUE;
        int queueSize=Integer.MAX_VALUE;
        Elevator requiredElevator=null;

        for(Elevator elevator: elevatorMap.values()){
            int currDistance=0;
            int currQueueSize=elevator.getDownRequests().size()+elevator.getUpRequests().size();
            if(ElevatorState.MOVING_DOWN.equals(elevator.getState())){
                if(elevator.getCurrentFloor()<requestedFloor){
                    currDistance=requestedFloor+elevator.getCurrentFloor()-1;
                }else{
                    currDistance=elevator.getCurrentFloor()-requestedFloor;
                }

            }else if(ElevatorState.MOVING_UP.equals(elevator.getState())){
                if(elevator.getCurrentFloor()>requestedFloor){
                    currDistance=elevator.getMaxFloor()-elevator.getCurrentFloor()+elevator.getMaxFloor()-requestedFloor;
                }

            }else if(ElevatorState.IDLE.equals(elevator.getState())){
                currDistance=abs(elevator.getCurrentFloor()-requestedFloor);
            }
            if(currQueueSize<queueSize){
                queueSize=currQueueSize;
                if(currDistance<=distance){
                    distance=currDistance;
                    requiredElevator=elevator;

                }
            }

        }
        return requiredElevator;
    }
}
