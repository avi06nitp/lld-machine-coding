# Hotel Booking System

**Difficulty:** ⭐⭐⭐☆☆ (Medium)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a hotel room booking system that manages room inventory and reservations.

---

## Requirements

### Must Have (Core)
- Add rooms with types (Single, Double, Suite) and prices
- Search available rooms for date range
- Book room for a guest
- Cancel booking (full refund if 24+ hours before, 50% otherwise)
- Check-in / Check-out
- View booking details

### Good to Have
- Multiple hotels
- Room amenities (AC, WiFi, etc.)

### Not Expected
- Payment processing
- Room service
- Dynamic pricing
- Reviews/ratings

---

## Expected Input/Output

```
Input:
> add_room R101 SINGLE 100
> add_room R102 DOUBLE 150
> add_room R103 SUITE 300
> search_rooms 2024-02-01 2024-02-03 DOUBLE
> book R102 G1 "Alice" 2024-02-01 2024-02-03
> view_booking BK-001
> cancel_booking BK-001 2024-01-30    # 2 days before check-in

Output:
Room R101 added: SINGLE at $100/night
Room R102 added: DOUBLE at $150/night
Room R103 added: SUITE at $300/night

Available DOUBLE rooms (2024-02-01 to 2024-02-03):
1. R102 - DOUBLE - $150/night - Total: $300 (2 nights)

Booking confirmed:
  Booking ID: BK-001
  Guest: Alice (G1)
  Room: R102 (DOUBLE)
  Check-in: 2024-02-01
  Check-out: 2024-02-03
  Nights: 2
  Total: $300

Booking Details (BK-001):
  Guest: Alice
  Room: R102 (DOUBLE)
  Dates: 2024-02-01 to 2024-02-03
  Status: CONFIRMED
  Total: $300

Booking BK-001 cancelled
Cancellation date: 2024-01-30 (2 days before check-in)
Refund: $300 (Full refund - cancelled 24+ hours before)
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles booking flow |
| **OO Design** | 25% | Proper modeling of Room, Booking, Guest |
| **Design Patterns** | 20% | Factory, Builder, Strategy |
| **Code Quality** | 15% | Clean code, date handling |
| **Extensibility** | 10% | Easy to add room types, policies |
| **Edge Cases** | 5% | Overlapping bookings, invalid dates |

---

## Follow-up Questions

### Design & Architecture
1. How would you handle overbooking (like airlines)?
2. How would you implement seasonal pricing?
3. How would you support room upgrades?
4. How would you add package deals (room + meals)?

### Concurrency
5. How would you handle two users booking the same room simultaneously?
6. How would you implement a waitlist for sold-out dates?
7. How would you handle booking modifications?

### Extensibility
8. How would you integrate with external booking platforms (Booking.com)?
9. How would you add loyalty points system?
10. How would you implement different cancellation policies per room type?

---

## Hints for Candidate

- Room availability is date-range based, not just a boolean
- Consider an Availability/Calendar class per room
- Booking should track state (PENDING, CONFIRMED, CHECKED_IN, CANCELLED)
- Cancellation policy could be a Strategy
