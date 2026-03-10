package strategy;

import model.DeliveryAgent;
import model.Order;

import java.util.List;

public interface DeliveryAssignmentStrategy {
    DeliveryAgent assignAgent(Order order, List<DeliveryAgent> availableAgents);
}
