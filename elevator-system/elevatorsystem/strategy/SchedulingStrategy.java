package elevatorsystem.strategy;

import elevatorsystem.models.Elevator;

import java.util.Collection;

public interface SchedulingStrategy {
    Elevator pick(Collection<Elevator> elevators, int requestedFloor);
}