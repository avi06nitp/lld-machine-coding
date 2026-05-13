package elevatorsystem.strategy;

import elevatorsystem.enums.ElevatorState;
import elevatorsystem.models.Elevator;

import java.util.Collection;

import static java.lang.Math.abs;

public class NearestLoadAwareStrategy implements SchedulingStrategy {

    @Override
    public Elevator pick(Collection<Elevator> elevators, int requestedFloor) {
        int bestDistance = Integer.MAX_VALUE;
        int bestQueueSize = Integer.MAX_VALUE;
        Elevator best = null;

        for (Elevator elevator : elevators) {
            int distance = distanceFor(elevator, requestedFloor);
            int queueSize = elevator.totalPendingRequests();
            if (queueSize < bestQueueSize) {
                bestQueueSize = queueSize;
                if (distance <= bestDistance) {
                    bestDistance = distance;
                    best = elevator;
                }
            }
        }
        return best;
    }

    private int distanceFor(Elevator elevator, int requestedFloor) {
        ElevatorState state = elevator.getState();
        int current = elevator.getCurrentFloor();
        if (state == ElevatorState.MOVING_DOWN) {
            return current < requestedFloor
                    ? requestedFloor + current - 1
                    : current - requestedFloor;
        }
        if (state == ElevatorState.MOVING_UP) {
            if (current > requestedFloor) {
                return elevator.getMaxFloor() - current + elevator.getMaxFloor() - requestedFloor;
            }
            return 0;
        }
        return abs(current - requestedFloor);
    }
}