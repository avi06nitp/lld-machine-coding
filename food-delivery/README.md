# Food Delivery App (Zomato Clone)

A Low-Level Design implementation of a food delivery platform demonstrating the **Observer** and **Strategy** design patterns along with SOLID principles.

## Problem Statement

Design a food delivery system where:
- Customers can browse restaurants, add items to a cart, and place orders
- Orders go through a lifecycle: PLACED → ACCEPTED → PREPARING → READY_FOR_PICKUP → PICKED_UP → DELIVERED
- Users, restaurants, and delivery agents are notified on every status change
- A delivery agent is auto-assigned when an order is placed using a pluggable assignment strategy
- Orders can be cancelled if not yet out for delivery

## Design Patterns Used

### Observer Pattern — Order Status Notifications
`Order` maintains a list of `OrderObserver` subscribers. Whenever the status changes, all observers are notified automatically.

| Observer | Notifies |
|----------|----------|
| `UserNotificationObserver` | Customer |
| `RestaurantNotificationObserver` | Restaurant |
| `DeliveryAgentNotificationObserver` | Delivery agent |

### Strategy Pattern — Delivery Assignment
`DeliveryService` delegates agent selection to an interchangeable `DeliveryAssignmentStrategy`.

| Strategy | Logic |
|----------|-------|
| `NearestAgentStrategy` | Picks first available agent (geo-proximity in real use) |
| `LeastOrderCountStrategy` | Picks agent with fewest completed deliveries (load balancing) |

## Project Structure

```
food-delivery/
├── FoodDeliveryApp.java          # Entry point / demo
├── enums/
│   ├── OrderStatus.java          # PLACED, ACCEPTED, PREPARING, READY_FOR_PICKUP, PICKED_UP, DELIVERED, CANCELLED
│   ├── DeliveryAgentStatus.java  # AVAILABLE, BUSY
│   └── FoodCategory.java         # VEG, NON_VEG, VEGAN
├── model/
│   ├── User.java
│   ├── Restaurant.java
│   ├── MenuItem.java
│   ├── OrderItem.java
│   ├── Cart.java
│   ├── DeliveryAgent.java
│   └── Order.java                # Holds observers, notifies on status change
├── observer/
│   ├── OrderObserver.java        # Interface
│   ├── UserNotificationObserver.java
│   ├── RestaurantNotificationObserver.java
│   └── DeliveryAgentNotificationObserver.java
├── strategy/
│   ├── DeliveryAssignmentStrategy.java  # Interface
│   ├── NearestAgentStrategy.java
│   └── LeastOrderCountStrategy.java
├── service/
│   ├── RestaurantService.java
│   ├── DeliveryService.java
│   └── OrderService.java
├── exception/
│   ├── FoodDeliveryException.java
│   ├── RestaurantNotFoundException.java
│   ├── OrderNotFoundException.java
│   ├── NoDeliveryAgentAvailableException.java
│   └── InvalidOrderStateException.java
└── controller/
    └── FoodDeliveryController.java
```

## How to Run

```bash
cd food-delivery
javac -d out $(find . -name "*.java")
java -cp out FoodDeliveryApp
```

## SOLID Principles Applied

| Principle | Application |
|-----------|-------------|
| **S**ingle Responsibility | Each class has one job: `OrderService` manages order lifecycle, `DeliveryService` manages agents, etc. |
| **O**pen/Closed | New notification types extend `OrderObserver`; new assignment algorithms extend `DeliveryAssignmentStrategy` — no existing code changes |
| **L**iskov Substitution | Any `DeliveryAssignmentStrategy` or `OrderObserver` implementation is interchangeable |
| **I**nterface Segregation | `OrderObserver` has a single focused method |
| **D**ependency Inversion | `DeliveryService` depends on the `DeliveryAssignmentStrategy` abstraction, not concrete classes |
