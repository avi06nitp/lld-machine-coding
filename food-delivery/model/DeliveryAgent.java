package model;

import enums.DeliveryAgentStatus;

public class DeliveryAgent {
    private final String id;
    private final String name;
    private final String phone;
    private DeliveryAgentStatus status;
    private int totalOrdersDelivered;
    private String currentOrderId;

    public DeliveryAgent(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = DeliveryAgentStatus.AVAILABLE;
        this.totalOrdersDelivered = 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public DeliveryAgentStatus getStatus() { return status; }
    public int getTotalOrdersDelivered() { return totalOrdersDelivered; }
    public String getCurrentOrderId() { return currentOrderId; }

    public void setStatus(DeliveryAgentStatus status) { this.status = status; }
    public void setCurrentOrderId(String orderId) { this.currentOrderId = orderId; }
    public void incrementTotalOrdersDelivered() { this.totalOrdersDelivered++; }

    @Override
    public String toString() {
        return String.format("DeliveryAgent{id='%s', name='%s', status=%s}", id, name, status);
    }
}
