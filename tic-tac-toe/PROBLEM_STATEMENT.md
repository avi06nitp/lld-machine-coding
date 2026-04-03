# Tic-Tac-Toe

**Difficulty:** ⭐⭐☆☆☆ (Easy)
**Time:** 60-90 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a command-line Tic-Tac-Toe game that supports human vs human gameplay on a configurable board.

---

## Requirements

### Must Have (Core)
- Support N×N board (default 3×3)
- Two players take turns (X and O)
- Validate moves (cell already occupied, out of bounds)
- Detect win (row, column, diagonal)
- Detect draw (board full, no winner)
- Display board after each move

### Good to Have
- Undo last move
- Player vs Computer (random moves)

### Not Expected
- AI with Minimax algorithm
- Network multiplayer
- GUI

---

## Expected Input/Output

```
Input:
> start 3
> move 1 1
> move 0 0
> move 1 0
> move 1 2
> move 0 1
> move 2 2
> move 2 1

Output:
Game started with 3x3 board

  |   |
---------
  |   |
---------
  |   |

Player X's turn
Move accepted: (1,1)

  |   |
---------
  | X |
---------
  |   |

Player O's turn
Move accepted: (0,0)

O |   |
---------
  | X |
---------
  |   |

... (continues)

Game Over! Player X wins!
Winning pattern: Column 1
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles basic game flow |
| **OO Design** | 25% | Proper class hierarchy, encapsulation |
| **Design Patterns** | 20% | Strategy for player types, Factory |
| **Code Quality** | 15% | Clean code, meaningful names |
| **Extensibility** | 10% | Easy to add new features |
| **Edge Cases** | 5% | Invalid moves, boundary conditions |

---

## Follow-up Questions

### Design & Architecture
1. How would you extend this to support Connect-4 or Gomoku?
2. How would you add different win conditions (like 4-in-a-row on 5x5 board)?
3. How would you implement a smart AI player using Minimax?
4. How would you support network multiplayer?

### Optimization
5. What's the time complexity of your win detection? Can you make it O(1)?
6. How would you handle a 1000×1000 board efficiently?

### SOLID Principles
7. Which SOLID principles did you apply? Give examples.
8. How does your design support adding new player types (AI, Remote) without modifying existing code?
9. How would you test the win detection logic?
10. How would you add a replay feature to watch past games?

---

## Hints for Candidate

- Start with the Board and basic move validation
- Win detection can be checked after each move (only check row/col/diag of last move)
- Consider using an interface for Player to support different player types
- Think about how Game orchestrates the flow between components
