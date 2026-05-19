package elevatorsystem.service;

import elevatorsystem.models.Elevator;
import elevatorsystem.registry.ElevatorRegistry;
import elevatorsystem.strategy.SchedulingStrategy;

public class ElevatorMatchingService {

    private final SchedulingStrategy strategy;

    public ElevatorMatchingService(SchedulingStrategy strategy) {
        this.strategy = strategy;
    }

    public Elevator findBestMatch(int requestedFloor) {
        return strategy.pick(ElevatorRegistry.getElevators().values(), requestedFloor);
    }
}