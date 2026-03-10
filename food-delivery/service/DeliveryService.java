package service;

import enums.DeliveryAgentStatus;
import exception.NoDeliveryAgentAvailableException;
import model.DeliveryAgent;
import model.Order;
import strategy.DeliveryAssignmentStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeliveryService {
    private final Map<String, DeliveryAgent> agents = new HashMap<>();
    private DeliveryAssignmentStrategy assignmentStrategy;

    public DeliveryService(DeliveryAssignmentStrategy assignmentStrategy) {
        this.assignmentStrategy = assignmentStrategy;
    }

    public void setAssignmentStrategy(DeliveryAssignmentStrategy strategy) {
        this.assignmentStrategy = strategy;
    }

    public void registerAgent(DeliveryAgent agent) {
        agents.put(agent.getId(), agent);
    }

    public DeliveryAgent assignDeliveryAgent(Order order) {
        List<DeliveryAgent> available = agents.values().stream()
            .filter(a -> a.getStatus() == DeliveryAgentStatus.AVAILABLE)
            .collect(Collectors.toList());

        DeliveryAgent agent = assignmentStrategy.assignAgent(order, available);
        if (agent == null) {
            throw new NoDeliveryAgentAvailableException("No delivery agents available for order: " + order.getId());
        }
        agent.setStatus(DeliveryAgentStatus.BUSY);
        agent.setCurrentOrderId(order.getId());
        return agent;
    }

    public void completeDelivery(String agentId) {
        DeliveryAgent agent = agents.get(agentId);
        if (agent != null) {
            agent.setStatus(DeliveryAgentStatus.AVAILABLE);
            agent.incrementTotalOrdersDelivered();
            agent.setCurrentOrderId(null);
        }
    }
}
