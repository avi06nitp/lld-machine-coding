package strategy;

import model.DeliveryAgent;
import model.Order;

import java.util.List;

/**
 * Assigns the first available delivery agent.
 * In a real system this would use geolocation to find the nearest agent.
 */
public class NearestAgentStrategy implements DeliveryAssignmentStrategy {
    @Override
    public DeliveryAgent assignAgent(Order order, List<DeliveryAgent> availableAgents) {
        if (availableAgents.isEmpty()) return null;
        // Simplified: return first available (real impl would use geo-distance)
        return availableAgents.get(0);
    }
}
