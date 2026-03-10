package strategy;

import model.DeliveryAgent;
import model.Order;

import java.util.Comparator;
import java.util.List;

/**
 * Assigns the delivery agent who has delivered the fewest orders,
 * ensuring equitable workload distribution.
 */
public class LeastOrderCountStrategy implements DeliveryAssignmentStrategy {
    @Override
    public DeliveryAgent assignAgent(Order order, List<DeliveryAgent> availableAgents) {
        if (availableAgents.isEmpty()) return null;
        return availableAgents.stream()
            .min(Comparator.comparingInt(DeliveryAgent::getTotalOrdersDelivered))
            .orElse(null);
    }
}
