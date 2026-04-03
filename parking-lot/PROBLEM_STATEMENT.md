# Parking Lot System

**Difficulty:** ⭐⭐⭐☆☆ (Easy-Medium)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a multi-floor parking lot system that manages vehicle entry, exit, and slot allocation across different vehicle types.

---

## Requirements

### Must Have (Core)
- Support multiple vehicle types: TWO_WHEELER, FOUR_WHEELER, HEAVY_VEHICLE
- Multiple floors, each with a configurable number of slots per vehicle type
- Assign the nearest available slot on entry
- Release slot on exit and calculate parking fee
- Display current availability per floor and vehicle type

### Good to Have
- Reserved/VIP slots
- Hourly and flat-rate pricing strategies
- Receipt generation on exit

### Not Expected
- Real-time sensor integration
- Payment processing
- Mobile app interface
- Database persistence

---

## Expected Input/Output

```
Input:
> create_lot 3 FLOORS
> add_slots FLOOR_1 TWO_WHEELER 10
> add_slots FLOOR_1 FOUR_WHEELER 5
> add_slots FLOOR_2 FOUR_WHEELER 8
> add_slots FLOOR_3 HEAVY_VEHICLE 4
> park TWO_WHEELER KA-01-HH-1234
> park FOUR_WHEELER MH-12-AB-9876
> park FOUR_WHEELER DL-05-CD-3322
> availability FOUR_WHEELER
> exit KA-01-HH-1234 2   # 2 hours
> exit MH-12-AB-9876 5

Output:
Parking lot created with 3 floors.

Slots added: FLOOR_1 | TWO_WHEELER | 10 slots
Slots added: FLOOR_1 | FOUR_WHEELER | 5 slots
Slots added: FLOOR_2 | FOUR_WHEELER | 8 slots
Slots added: FLOOR_3 | HEAVY_VEHICLE | 4 slots

Parked: KA-01-HH-1234
  Slot: FLOOR_1 | TWO_WHEELER | Slot-1
  Entry Time: 10:00

Parked: MH-12-AB-9876
  Slot: FLOOR_1 | FOUR_WHEELER | Slot-1
  Entry Time: 10:05

Parked: DL-05-CD-3322
  Slot: FLOOR_1 | FOUR_WHEELER | Slot-2
  Entry Time: 10:10

Availability for FOUR_WHEELER:
  FLOOR_1: 3 / 5 slots available
  FLOOR_2: 8 / 8 slots available

Exit: KA-01-HH-1234
  Duration: 2 hours
  Fee: ₹40 (₹20/hr for TWO_WHEELER)
  Slot FLOOR_1 | TWO_WHEELER | Slot-1 is now free.

Exit: MH-12-AB-9876
  Duration: 5 hours
  Fee: ₹150 (₹30/hr for FOUR_WHEELER)
  Slot FLOOR_1 | FOUR_WHEELER | Slot-1 is now free.
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles park/exit flow |
| **OO Design** | 25% | Clean abstraction for lot, floor, slot, vehicle |
| **Design Patterns** | 20% | Factory for vehicle, Singleton for lot manager |
| **Code Quality** | 15% | Readable, modular, proper naming |
| **Extensibility** | 10% | Easy to add new vehicle types or floors |
| **Edge Cases** | 5% | Lot full, invalid vehicle, double-exit |

---

## Follow-up Questions

### Design & Architecture
1. How would you model the nearest-slot allocation — would you use a priority queue per floor?
2. How would you support multiple pricing strategies (hourly, flat, weekend rates)?
3. How would you extend this to support EV charging slots?
4. How would you handle reserved slots for monthly subscribers?

### Concurrency
5. What happens if two vehicles try to claim the same slot simultaneously?
6. How would you make slot allocation thread-safe without a global lock?
7. How would you implement optimistic locking for slot reservation?

### Scalability
8. How would you handle a parking lot with 10,000 slots across 20 floors?
9. How would you persist slot state across system restarts?
10. How would you design this for a chain of parking lots across a city?

---

## Slot Lifecycle

```
Slot States:
FREE → OCCUPIED → FREE

Vehicle Entry:
  Find nearest FREE slot → Mark OCCUPIED → Issue ticket

Vehicle Exit:
  Validate ticket → Calculate fee → Mark slot FREE → Print receipt
```

---

## Core Interfaces

```java
interface Vehicle {
    String getRegistrationNumber();
    VehicleType getType();
}

interface ParkingSlot {
    String getSlotId();
    VehicleType getSupportedType();
    boolean isAvailable();
    void assignVehicle(Vehicle vehicle);
    void removeVehicle();
}

interface PricingStrategy {
    double calculateFee(VehicleType type, long durationInHours);
}
```

---

## Hints for Candidate

- Use Factory pattern to instantiate different vehicle types
- Use Singleton for the central ParkingLotManager
- Each Floor should manage its own slot buckets per vehicle type
- Keep Ticket immutable — create on entry, read on exit
- Decouple fee calculation using a PricingStrategy interface