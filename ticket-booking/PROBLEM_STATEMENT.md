# Event Ticket Booking System

**Difficulty:** ⭐⭐⭐☆☆ (Medium)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a ticket booking system for events with seat selection.

---

## Requirements

### Must Have (Core)
- Create events with venue and seating
- View available seats
- Book seats (with temporary hold)
- Confirm or release booking
- Prevent double booking
- View booking details

### Good to Have
- Different ticket categories (VIP, Regular)
- Bulk booking discount

### Not Expected
- Payment processing
- E-ticket generation
- Waitlist
- Recurring events

---

## Expected Input/Output

```
Input:
> create_event E1 "Rock Concert" "2024-03-15" "Stadium"
> add_seats E1 A 1-20 VIP 100.00
> add_seats E1 B 1-50 REGULAR 50.00
> view_seats E1
> hold_seats E1 U1 A-1,A-2,A-3
> hold_seats E1 U2 A-1    # Should fail
> confirm_booking BK-001
> release_booking BK-002    # If any was created
> view_booking BK-001

Output:
Event created:
  ID: E1
  Name: Rock Concert
  Date: 2024-03-15
  Venue: Stadium

Added seats A1-A20 (VIP @ $100.00)
Added seats B1-B50 (REGULAR @ $50.00)

Available seats for E1:
VIP (A): A1, A2, A3, ... A20 (20 available) - $100.00
REGULAR (B): B1, B2, ... B50 (50 available) - $50.00
Total: 70 seats

Seats held for U1:
  Booking ID: BK-001
  Seats: A-1, A-2, A-3
  Category: VIP
  Total: $300.00
  Hold expires in: 10 minutes
  Status: HELD

Error: Seat A-1 is already held

Booking BK-001 confirmed
  User: U1
  Event: Rock Concert (2024-03-15)
  Seats: A-1, A-2, A-3
  Total: $300.00
  Status: CONFIRMED

Booking Details (BK-001):
  Event: Rock Concert
  Date: 2024-03-15
  Venue: Stadium
  Seats: A-1, A-2, A-3 (VIP)
  Amount: $300.00
  Status: CONFIRMED
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles booking flow |
| **OO Design** | 25% | Proper modeling of Event, Seat, Booking |
| **Design Patterns** | 20% | State, Factory |
| **Code Quality** | 15% | Clean code, concurrency awareness |
| **Extensibility** | 10% | Easy to add seat types |
| **Edge Cases** | 5% | Concurrent bookings, expired holds |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement the seat hold timeout?
2. How would you handle concurrent booking attempts?
3. How would you support multiple pricing tiers?
4. How would you add season tickets / subscription model?

### Edge Cases
5. What if user tries to book during hold period by another user?
6. How would you handle partial bookings (some seats unavailable)?
7. How would you implement seat adjacency preference?

### Scalability
8. How would you handle flash sales (10000 users booking simultaneously)?
9. How would you implement a fair queuing system?
10. How would you design for multiple venues/events?

---

## Booking State Machine

```
AVAILABLE → HELD → CONFIRMED
              ↓
          (timeout)
              ↓
          AVAILABLE

States:
- AVAILABLE: Seat can be selected
- HELD: Temporarily reserved, pending payment
- CONFIRMED: Booking complete, seat sold
- (CANCELLED): Returned to AVAILABLE
```

---

## Hints for Candidate

- Seat has state (AVAILABLE, HELD, BOOKED)
- Booking tracks seats and has its own state
- Consider a timer/scheduler for hold expiration
- Use optimistic locking for concurrent access
