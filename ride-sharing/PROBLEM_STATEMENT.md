# Ride-Sharing App (Uber Clone)

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a ride-sharing application with driver matching and fare calculation.

---

## Requirements

### Must Have (Core)
- Register riders and drivers
- Driver availability (online/offline)
- Request ride (pickup, destination)
- Match with nearest available driver
- Calculate fare (base + per km + per minute)
- Complete ride and rate driver

### Good to Have
- Multiple vehicle types with different pricing
- Driver ratings affecting matching

### Not Expected
- Real-time tracking
- Surge pricing
- Route optimization
- Payment processing

---

## Expected Input/Output

```
Input:
> register_rider R1 "Alice" 10.0 20.0
> register_driver D1 "John" SEDAN 10.5 20.5
> register_driver D2 "Jane" SUV 15.0 25.0
> set_online D1
> set_online D2
> request_ride R1 10.0 20.0 15.0 25.0
> accept_ride RIDE-001 D1
> start_ride RIDE-001
> complete_ride RIDE-001 20    # 20 minutes
> rate_driver RIDE-001 5

Output:
Rider R1 "Alice" registered

Driver D1 "John" registered with SEDAN
Driver D2 "Jane" registered with SUV

D1 is now ONLINE
D2 is now ONLINE

Ride requested:
  Ride ID: RIDE-001
  Rider: Alice
  Pickup: (10.0, 20.0)
  Destination: (15.0, 25.0)
  Distance: 7.07 km

Finding nearby drivers...
Available drivers:
  1. D1 (John) - SEDAN - 0.7 km away
  2. D2 (Jane) - SUV - 7.1 km away

Ride RIDE-001 accepted by John (D1)
Estimated fare: $25.35 (SEDAN)
  Base: $5.00
  Distance (7.07 km × $2.00): $14.14
  Time (est. 15 min × $0.50): $7.50

Driver en route to pickup...

Ride RIDE-001 started
  Start time: 10:30:00

Ride RIDE-001 completed
  End time: 10:50:00
  Duration: 20 minutes
  Distance: 7.07 km

  Fare breakdown:
    Base fare: $5.00
    Distance: $14.14
    Time: $10.00
    Total: $29.14

Rating submitted: 5 stars for John
John's new rating: 4.8 (156 rides)
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles ride flow |
| **OO Design** | 25% | Proper modeling of Rider, Driver, Ride |
| **Design Patterns** | 20% | Strategy, Observer, State |
| **Code Quality** | 15% | Clean code, separation of concerns |
| **Extensibility** | 10% | Easy to add vehicle types |
| **Edge Cases** | 5% | No drivers, cancellation |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement surge pricing?
2. How would you handle ride cancellation?
3. How would you implement ride pooling (shared rides)?
4. How would you add scheduled rides (book for later)?

### Optimization
5. How would you efficiently find nearest driver among millions?
6. How would you handle concurrent ride requests?
7. What data structure would you use for location queries?

### Scalability
8. How would you design this for multiple cities?
9. How would you implement driver incentives/bonuses?
10. How would you add support for multiple stops?

---

## Ride State Machine

```
REQUESTED → ACCEPTED → DRIVER_ARRIVED → IN_PROGRESS → COMPLETED
    ↓           ↓            ↓
CANCELLED   CANCELLED   CANCELLED

States:
- REQUESTED: Rider requested, finding driver
- ACCEPTED: Driver accepted, en route to pickup
- DRIVER_ARRIVED: Driver at pickup location
- IN_PROGRESS: Ride started, en route to destination
- COMPLETED: Ride finished, payment processed
- CANCELLED: Cancelled by rider or driver
```

---

## Fare Calculation

```
Fare = BaseFare + (Distance × PerKmRate) + (Time × PerMinRate)

Vehicle Type Rates:
| Type  | Base | Per Km | Per Min |
|-------|------|--------|---------|
| MINI  | $3   | $1.50  | $0.30   |
| SEDAN | $5   | $2.00  | $0.50   |
| SUV   | $8   | $3.00  | $0.75   |
```

---

## Hints for Candidate

- Use Euclidean distance for simplicity
- Strategy pattern for different matching algorithms
- State pattern for ride status transitions
- Consider a RideService to orchestrate the flow
