# Cab Booking System

**Difficulty:** ⭐⭐⭐☆☆ (Medium)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a simplified cab booking system that matches riders with nearby available drivers.

---

## Requirements

### Must Have (Core)
- Register drivers with cab details
- Driver availability toggle (online/offline)
- Book cab: find nearest available driver
- Complete ride: calculate fare (base + per km)
- Ride history for users
- Driver can accept/reject ride

### Good to Have
- Multiple cab types (Mini, Sedan, SUV) with different pricing
- Driver rating system

### Not Expected
- Real-time location tracking
- Route optimization
- Surge pricing algorithm
- Payment processing

---

## Expected Input/Output

```
Input:
> register_driver D1 "John" SEDAN 10.5 20.5    # location (10.5, 20.5)
> register_driver D2 "Jane" MINI 12.0 22.0
> register_rider R1 "Alice"
> driver_online D1
> driver_online D2
> book_ride R1 10.0 20.0 15.0 25.0    # pickup(10,20) drop(15,25)
> complete_ride RIDE-001
> ride_history R1

Output:
Driver D1 (John) registered with SEDAN

Driver D2 (Jane) registered with MINI

Rider R1 (Alice) registered

Driver D1 is now ONLINE
Driver D2 is now ONLINE

Searching for cabs near (10.0, 20.0)...
Found 2 available drivers
Nearest driver: D1 (John) - 0.7 km away
Ride RIDE-001 booked
Driver: John (SEDAN)
Pickup: (10.0, 20.0)
Drop: (15.0, 25.0)
Estimated fare: $45

Ride RIDE-001 completed
Distance: 7.07 km
Fare breakdown:
  Base fare: $10
  Distance (7.07 km × $5): $35.35
  Total: $45.35

Ride History for Alice:
1. RIDE-001 | John (SEDAN) | (10,20) → (15,25) | $45.35 | COMPLETED
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles booking flow |
| **OO Design** | 25% | Proper modeling of Driver, Rider, Ride |
| **Design Patterns** | 20% | Strategy for matching, Observer for status |
| **Code Quality** | 15% | Clean code, separation of concerns |
| **Extensibility** | 10% | Easy to add cab types, pricing |
| **Edge Cases** | 5% | No drivers available, driver rejection |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement surge pricing?
2. How would you handle ride cancellation (by rider vs driver)?
3. How would you match riders with preferred cab type?
4. How would you implement scheduled rides (book for later)?

### Optimization
5. How would you efficiently find nearest driver among 100,000 drivers?
6. How would you handle concurrent booking requests for the same driver?
7. What data structure would you use for location-based queries?

### Scalability
8. How would you design this for multiple cities?
9. How would you handle driver incentives/bonuses?
10. How would you implement ride pooling (shared rides)?

---

## Hints for Candidate

- Use Euclidean distance for simplicity (can mention Haversine for real-world)
- Consider a DriverMatchingStrategy interface
- Ride should track state transitions (REQUESTED → ACCEPTED → STARTED → COMPLETED)
- Separate fare calculation into its own service
