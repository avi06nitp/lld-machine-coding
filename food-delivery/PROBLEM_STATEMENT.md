# Food Delivery App (Zomato Clone)

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a food delivery application with restaurant management, ordering, and delivery tracking.

---

## Requirements

### Must Have (Core)
- Register restaurants with menu items
- Search restaurants (by name, cuisine)
- Place order (multiple items from one restaurant)
- Assign delivery agent to order
- Order status tracking (PLACED → CONFIRMED → PREPARING → OUT_FOR_DELIVERY → DELIVERED)
- View order history

### Good to Have
- Restaurant ratings
- Estimated delivery time

### Not Expected
- Payment processing
- Real-time location tracking
- Route optimization
- Discounts/coupons

---

## Expected Input/Output

```
Input:
> register_restaurant R1 "Pizza Palace" "Italian" 10.0 20.0
> add_menu_item R1 "Margherita Pizza" 12.99
> add_menu_item R1 "Pepperoni Pizza" 14.99
> add_menu_item R1 "Garlic Bread" 5.99
> register_user U1 "Alice" 12.0 22.0
> register_agent A1 "John" 11.0 21.0
> search_restaurant "Pizza"
> view_menu R1
> place_order U1 R1 "Margherita Pizza:2,Garlic Bread:1"
> update_status ORD-001 CONFIRMED
> assign_agent ORD-001 A1
> order_details ORD-001

Output:
Restaurant R1 "Pizza Palace" registered (Italian)

Menu item added: Margherita Pizza - $12.99
Menu item added: Pepperoni Pizza - $14.99
Menu item added: Garlic Bread - $5.99

User U1 "Alice" registered

Agent A1 "John" registered

Search results for "Pizza":
1. Pizza Palace (Italian) - 2.2 km away

Menu for Pizza Palace:
1. Margherita Pizza - $12.99
2. Pepperoni Pizza - $14.99
3. Garlic Bread - $5.99

Order placed:
  Order ID: ORD-001
  Restaurant: Pizza Palace
  Items:
    - Margherita Pizza x2 = $25.98
    - Garlic Bread x1 = $5.99
  Subtotal: $31.97
  Delivery fee: $3.00
  Total: $34.97
  Status: PLACED

Order ORD-001 status updated: CONFIRMED

Agent John assigned to ORD-001
Estimated delivery: 30 mins

Order Details (ORD-001):
  Customer: Alice
  Restaurant: Pizza Palace
  Items: Margherita Pizza x2, Garlic Bread x1
  Total: $34.97
  Status: CONFIRMED
  Agent: John (A1)
  ETA: 30 mins
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles order flow |
| **OO Design** | 25% | Proper modeling of entities |
| **Design Patterns** | 20% | State, Observer, Factory |
| **Code Quality** | 15% | Clean code, separation of concerns |
| **Extensibility** | 10% | Easy to add features |
| **Edge Cases** | 5% | Out of stock, no agents |

---

## Follow-up Questions

### Design & Architecture
1. How would you handle orders from multiple restaurants?
2. How would you implement restaurant search by location?
3. How would you handle item customization (no onion, extra cheese)?
4. How would you add scheduled orders (order for later)?

### Concurrency
5. How would you handle item going out of stock during order?
6. How would you assign agents optimally?
7. How would you handle concurrent orders to same restaurant?

### Scalability
8. How would you handle peak hour traffic?
9. How would you implement a rating and review system?
10. How would you add support for restaurant promotions?

---

## Order State Machine

```
PLACED → CONFIRMED → PREPARING → OUT_FOR_DELIVERY → DELIVERED
   ↓         ↓           ↓              ↓
CANCELLED CANCELLED  CANCELLED      (no cancel)

Transitions:
- PLACED: Order received, waiting for restaurant confirmation
- CONFIRMED: Restaurant accepted, preparing food
- PREPARING: Food being prepared
- OUT_FOR_DELIVERY: Agent picked up, en route
- DELIVERED: Order completed
- CANCELLED: Order cancelled (allowed until OUT_FOR_DELIVERY)
```

---

## Hints for Candidate

- Use State pattern for Order status transitions
- Restaurant has Menu, Menu has MenuItems
- Order has OrderItems (MenuItem + quantity)
- Agent assignment could use Strategy pattern
