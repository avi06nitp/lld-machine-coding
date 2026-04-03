# Elevator System

**Difficulty:** ⭐⭐⭐☆☆ (Medium)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement an elevator control system for a building with multiple elevators.

---

## Requirements

### Must Have (Core)
- Support multiple elevators in a building
- External requests (floor button: UP/DOWN)
- Internal requests (destination floor from inside elevator)
- Basic scheduling algorithm (nearest elevator)
- Display elevator status (current floor, direction, state)
- Move elevators step by step (simulate)

### Good to Have
- SCAN algorithm (elevator continues in direction until no more requests)
- Different elevator types (passenger, freight)

### Not Expected
- Real-time optimization
- Weight/capacity management
- Emergency protocols
- Actual concurrent threads

---

## Expected Input/Output

```
Input:
> init_building 10 3    # 10 floors, 3 elevators
> status
> call 5 UP
> call 3 DOWN
> select E1 7           # Inside E1, press 7
> step                   # Move elevators one floor
> step
> step
> status

Output:
Building initialized: 10 floors, 3 elevators

Status:
  E1: Floor 0 | IDLE | Doors: CLOSED
  E2: Floor 0 | IDLE | Doors: CLOSED
  E3: Floor 0 | IDLE | Doors: CLOSED

External request: Floor 5, Direction UP
Assigned to E1

External request: Floor 3, Direction DOWN
Assigned to E2

Internal request: E1 → Floor 7
Added to E1's destination queue

Step 1:
  E1: Moving UP (0 → 1)
  E2: Moving UP (0 → 1)
  E3: IDLE at 0

Step 2:
  E1: Moving UP (1 → 2)
  E2: Moving UP (1 → 2)
  E3: IDLE at 0

Step 3:
  E1: Moving UP (2 → 3)
  E2: Moving UP (2 → 3)
  E3: IDLE at 0

Status:
  E1: Floor 3 | MOVING_UP | Destinations: [5, 7]
  E2: Floor 3 | STOPPING | Destinations: []
  E3: Floor 0 | IDLE | Doors: CLOSED
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles requests |
| **OO Design** | 25% | Proper modeling of Elevator, Building |
| **Design Patterns** | 20% | State pattern, Strategy for scheduling |
| **Code Quality** | 15% | Clean code, separation of concerns |
| **Extensibility** | 10% | Easy to add new scheduling algorithms |
| **Edge Cases** | 5% | All elevators busy, requests at extremes |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement SCAN vs LOOK algorithm?
2. How would you prioritize requests during peak hours?
3. How would you handle VIP floors (penthouse)?
4. How would you implement different elevator types (freight, service)?

### Optimization
5. How would you minimize average wait time?
6. How would you balance load among elevators?
7. How would you handle morning rush (everyone going up)?

### Edge Cases
8. What if all elevators are at floor 10 and request comes at floor 1?
9. How would you handle elevator maintenance mode?
10. How would you implement floor access restrictions (keycard)?

---

## Scheduling Algorithms

### FCFS (First Come First Serve)
- Simple queue, process in order
- Not optimal but easy to implement

### SCAN (Elevator Algorithm)
- Continue in one direction until end
- Reverse and process requests
- Like a disk head

### LOOK
- Like SCAN but reverses at last request, not end
- More efficient than SCAN

### Nearest First
- Always pick nearest request
- Can cause starvation

---

## Hints for Candidate

- State pattern works well for Elevator (IDLE, MOVING_UP, MOVING_DOWN, STOPPED)
- Strategy pattern for different scheduling algorithms
- External requests need direction, internal requests need destination
- Consider how to merge internal and external requests efficiently
