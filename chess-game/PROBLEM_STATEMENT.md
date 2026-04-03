# Multiplayer Online Chess Game

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a chess game with move validation and game state management.

---

## Requirements

### Must Have (Core)
- Initialize board with standard setup
- Make move (validate legal moves for each piece)
- Turn management (white, then black)
- Detect check and checkmate
- Display board state
- Move history

### Good to Have
- Castling
- En passant

### Not Expected
- AI opponent
- Network multiplayer
- Chess timer
- Stalemate detection
- Draw conditions (50-move, repetition)

---

## Expected Input/Output

```
Input:
> new_game
> display
> move e2 e4
> move e7 e5
> move f1 c4
> move b8 c6
> move d1 h5
> move g8 f6
> move h5 f7
> display

Output:
New game started. White to move.

  a b c d e f g h
8 r n b q k b n r
7 p p p p p p p p
6 . . . . . . . .
5 . . . . . . . .
4 . . . . . . . .
3 . . . . . . . .
2 P P P P P P P P
1 R N B Q K B N R

White to move.

Move: e2 → e4 (Pawn)

Move: e7 → e5 (Pawn)

Move: f1 → c4 (Bishop)

Move: b8 → c6 (Knight)

Move: d1 → h5 (Queen)

Move: g8 → f6 (Knight)

Move: h5 → f7 (Queen captures pawn)
CHECK!

  a b c d e f g h
8 r . b q k b . r
7 p p p p . Q p p
6 . . n . . n . .
5 . . . . p . . .
4 . . B . P . . .
3 . . . . . . . .
2 P P P P . P P P
1 R N B . K . N R

Black King is in CHECK!
CHECKMATE! White wins.
(Scholar's Mate)
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, valid move detection |
| **OO Design** | 25% | Proper hierarchy for pieces |
| **Design Patterns** | 20% | Strategy/Template for piece movement |
| **Code Quality** | 15% | Clean code, readable move validation |
| **Extensibility** | 10% | Easy to add new pieces/rules |
| **Edge Cases** | 5% | Invalid moves, check blocking |

---

## Follow-up Questions

### Design & Architecture
1. How did you model different piece movements?
2. How would you implement castling rules?
3. How would you add undo functionality?
4. How would you implement PGN (chess notation) export?

### Validation
5. How do you check if a move puts own king in check?
6. How would you implement en passant?
7. How do you validate moves when king is in check?

### Extensibility
8. How would you add support for chess variants (960, etc.)?
9. How would you implement a move suggestion feature?
10. How would you add a chess clock?

---

## Piece Movement Rules

| Piece | Movement |
|-------|----------|
| Pawn | Forward 1 (or 2 from start), capture diagonal |
| Rook | Horizontal or vertical, any distance |
| Knight | L-shape (2+1), can jump |
| Bishop | Diagonal, any distance |
| Queen | Horizontal, vertical, or diagonal |
| King | One square in any direction |

---

## Check/Checkmate Logic

```
Check:
- King is attacked by opponent's piece

Checkmate:
- King is in check AND
- King cannot move to safe square AND
- No piece can block/capture attacker

To validate a move:
1. Execute move temporarily
2. Check if own king is in check
3. If yes, move is invalid
4. Revert move
```

---

## Hints for Candidate

- Abstract Piece class with concrete pieces (Pawn, Rook, etc.)
- Each piece has isValidMove(from, to, board) method
- Board is 8x8 array of Squares
- Move validation: piece-specific + no self-check
