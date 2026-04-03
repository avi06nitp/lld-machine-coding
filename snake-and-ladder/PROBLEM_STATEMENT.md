# Snake & Ladder

**Difficulty:** ⭐⭐☆☆☆ (Easy)
**Time:** 60-90 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a Snake & Ladder game that supports multiple players with configurable snakes and ladders.

---

## Requirements

### Must Have (Core)
- Configurable board size (default 100)
- Add snakes (head → tail, head > tail)
- Add ladders (bottom → top, bottom < top)
- Support 2-4 players
- Roll dice (1-6, fair random)
- Handle snake/ladder encounters
- Declare winner (first to reach/exceed final cell)

### Good to Have
- Support multiple dice
- Player cannot move if dice roll exceeds final cell (exact landing rule)
- Game history/replay

### Not Expected
- GUI
- Network multiplayer
- Power-ups or special cells

---

## Expected Input/Output

```
Input:
> init 100
> add_snake 99 10
> add_snake 65 25
> add_ladder 5 45
> add_ladder 20 80
> add_player Alice
> add_player Bob
> start
> roll  (assume dice shows 4)
> roll  (assume dice shows 5)
> roll  (assume dice shows 1)

Output:
Board initialized with 100 cells

Snake added: 99 → 10
Snake added: 65 → 25
Ladder added: 5 → 45
Ladder added: 20 → 80

Player Alice added
Player Bob added

Game started with 2 players

Alice's turn
Dice rolled: 4
Alice moved from 0 to 4

Bob's turn
Dice rolled: 5
Bob moved from 0 to 5
Bob climbed ladder at 5 to 45

Alice's turn
Dice rolled: 1
Alice moved from 4 to 5
Alice climbed ladder at 5 to 45

... (game continues until winner)

Alice wins! Reached position 100.
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles game flow |
| **OO Design** | 25% | Proper modeling of entities |
| **Design Patterns** | 20% | Factory, Strategy for dice |
| **Code Quality** | 15% | Clean code, single responsibility |
| **Extensibility** | 10% | Easy to add new cell types |
| **Edge Cases** | 5% | Snake at 99, ladder at 1, etc. |

---

## Follow-up Questions

### Design & Architecture
1. How would you prevent infinite loops (snake at ladder end, ladder at snake end)?
2. How would you add power-ups (extra roll, immunity from snake)?
3. How would you make dice biased for testing purposes?
4. How would you implement different cell types (skip turn, go back 5)?

### Extensibility
5. How would you support team-based play?
6. How would you add different board shapes (not just linear)?
7. How would adding a new dice type (8-sided) affect your code?

### SOLID Principles
8. How did you ensure Single Responsibility in your classes?
9. How does your design follow Open-Closed Principle?
10. How would you test the game logic without random dice?

---

## Hints for Candidate

- Board can be modeled as cells with optional snake/ladder
- Dice should be injectable for testing
- Consider separating game rules from game state
- Player movement should handle snake/ladder in sequence
