# Tic-Tac-Toe Game - Low-Level Design

A Low Level Design of Tic-Tac-Toe game implementation in Java, demonstrating software design principles, design patterns, and clean architecture.

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Design Patterns](#design-patterns)
- [SOLID Principles](#solid-principles)
- [Project Structure](#project-structure)
- [Key Components](#key-components)
- [Features](#features)
- [How to Run](#how-to-run)
- [Extensibility](#extensibility)

## Overview

This Tic-Tac-Toe implementation goes beyond a simple game to showcase professional software engineering practices. It's designed with scalability, maintainability, and extensibility in mind.

### Key Highlights
- **Clean Architecture**: Separation of concerns across layers
- **SOLID Principles**: Each class has a single, well-defined responsibility
- **Design Patterns**: Strategy, Factory, and State patterns
- **Extensible Design**: Easy to add new features (AI difficulty levels, different board sizes, new winning conditions)
- **Robust Error Handling**: Custom exceptions and validation
- **Type Safety**: Leveraging enums for state management

## Architecture

### Layered Architecture

```
┌─────────────────────────────────────┐
│      Presentation Layer             │
│    (Controller, Main Game)          │
├─────────────────────────────────────┤
│      Application Layer              │
│    (Services, Factories)            │
├─────────────────────────────────────┤
│      Domain Layer                   │
│    (Models, Strategies, Enums)      │
├─────────────────────────────────────┤
│      Infrastructure Layer           │
│    (Exceptions, Utilities)          │
└─────────────────────────────────────┘
```

### Component Interaction

```
User Input → GameController → GameService → GameState → Board/Players
                     ↓              ↓
              PlayerFactory   WinningStrategy
                     ↓              ↓
              PlayerStrategy  DefaultWinningStrategy
```

## Design Patterns

### 1. Strategy Pattern

**Used For**: Player behavior and winning conditions

```java
// Player Strategy - allows different player types
public interface PlayerStrategy {
    Move makeMove(Player player, Board board);
}
```

**Implementations**:
- `HumanPlayerStrategy`: Handles user input and validation
- `AIPlayerStrategy`: Implements AI logic (smart moves, blocking, etc.)

**Benefits**:
- Add new player types without modifying existing code
- Switch strategies at runtime
- Easy to test different AI algorithms

```java
// Winning Strategy - allows different winning conditions
public interface WinningStrategy {
    boolean checkWinner(Board board, Move lastMove);
}
```

**Current Implementation**:
- `DefaultWinningStrategy`: Standard Tic-Tac-Toe rules

**Future Extensions**:
- `ConnectFourStrategy`: Four-in-a-row winning condition
- `CustomWinningStrategy`: User-defined win patterns

### 2. Factory Pattern

**Used For**: Creating players with appropriate strategies

```java
public class PlayerFactory {
    public Player createPlayer(int playerNumber) {
        // Encapsulates complex player creation logic
        // Handles validation, strategy selection, etc.
    }
}
```

**Benefits**:
- Centralized player creation logic
- Consistent validation
- Easy to add new player types

### 3. State Pattern (Implicit)

**Used For**: Game state management

```java
public class GameState {
    private GameStatus status; // IN_PROGRESS, DRAW, WON
    // Manages current player, move history, winner
}
```

## SOLID Principles

### Single Responsibility Principle (SRP)
Each class has one reason to change:
- `Board`: Manages board state and cell operations
- `GameService`: Handles game logic and rules
- `ValidationService`: Validates user inputs
- `PlayerFactory`: Creates player instances
- `GameController`: Orchestrates game flow

### Open/Closed Principle (OCP)
Open for extension, closed for modification:
- Add new `PlayerStrategy` without changing existing player code
- Add new `WinningStrategy` without modifying game logic
- Extend with new player types through configuration

### Liskov Substitution Principle (LSP)
Subtypes are substitutable for their base types:
- Any `PlayerStrategy` can replace another
- Any `WinningStrategy` can replace another

### Interface Segregation Principle (ISP)
Clients depend only on methods they use:
- `PlayerStrategy` has single method: `makeMove()`
- `WinningStrategy` has single method: `checkWinner()`

### Dependency Inversion Principle (DIP)
Depend on abstractions, not concretions:
- `Player` depends on `PlayerStrategy` interface
- `GameService` depends on `WinningStrategy` interface
- High-level modules don't depend on low-level modules

## Project Structure

```
tic-tac-toe/
├── controller/
│   └── GameController.java        # Orchestrates game flow, handles I/O
│
├── enums/
│   ├── CellState.java             # EMPTY, FILLED
│   ├── GameStatus.java            # IN_PROGRESS, DRAW, WON
│   └── PlayerType.java            # HUMAN, AI
│
├── exception/
│   ├── GameException.java         # Base exception
│   ├── InvalidMoveException.java  # Invalid move errors
│   └── InvalidPlayerException.java # Player validation errors
│
├── factory/
│   └── PlayerFactory.java         # Creates player instances
│
├── model/
│   ├── Board.java                 # Board state and operations
│   ├── Cell.java                  # Individual cell with state
│   ├── GameState.java             # Complete game state
│   ├── Move.java                  # Represents a player's move
│   └── Player.java                # Player entity with strategy
│
├── service/
│   ├── GameService.java           # Core game logic
│   └── ValidationService.java     # Input validation
│
├── strategy/
│   ├── PlayerStrategy.java        # Interface for player behavior
│   ├── HumanPlayerStrategy.java   # Human player implementation
│   ├── AIPlayerStrategy.java      # AI player implementation
│   ├── WinningStrategy.java       # Interface for win detection
│   └── DefaultWinningStrategy.java # Standard winning rules
│
├── TicTacToeGame.java             # Main entry point
└── README.md                       # This file
```

## Key Components

### Domain Models

#### Board
- Manages the game board (NxN grid)
- Provides cell access and validation
- Tracks filled cells for draw detection
- Displays formatted board to console

#### Cell
- Represents individual board position
- Maintains state (EMPTY/FILLED) and symbol
- Immutable position coordinates

#### Player
- Encapsulates player information
- Delegates move logic to `PlayerStrategy`
- Type-safe with `PlayerType` enum

#### Move
- Immutable representation of a game move
- Links player with target cell
- Useful for move history and undo features

#### GameState
- Central state management
- Tracks current player, status, winner
- Maintains move history
- Handles player switching

### Services

#### GameService
- Core game logic and rules
- Validates players (unique symbols)
- Executes moves and checks game status
- Coordinates winning strategy

#### ValidationService
- Input validation (board size, names, symbols)
- Centralized validation logic
- Provides clear error messages

### Strategies

#### WinningStrategy
- Checks rows, columns, and diagonals
- Optimized to check only relevant positions after each move
- Extensible for different game modes

#### PlayerStrategy
- **HumanPlayerStrategy**: Interactive input with validation
- **AIPlayerStrategy**: Intelligent moves with:
  - Winning move detection
  - Blocking opponent's winning moves
  - Strategic positioning (center, corners)
  - Random fallback

## Features

### Current Features
✅ Configurable board size (3x3 to 10x10)
✅ Multiple players support (2+)
✅ Human vs Human mode
✅ Human vs AI mode
✅ AI vs AI mode
✅ Smart AI with strategic moves
✅ Input validation and error handling
✅ Move history tracking
✅ Play again functionality
✅ Beautiful console UI

### AI Intelligence
- **Priority 1**: Make winning move if available
- **Priority 2**: Block opponent's winning move
- **Priority 3**: Take center position (odd-sized boards)
- **Priority 4**: Take corner positions
- **Priority 5**: Take any available position

## How to Run

### Prerequisites
- Java 17 or higher
- No external dependencies required

### Compilation

```bash
# Navigate to the tic-tac-toe directory
cd tic-tac-toe

# Compile all Java files
javac TicTacToeGame.java \
      controller/*.java \
      enums/*.java \
      exception/*.java \
      factory/*.java \
      model/*.java \
      service/*.java \
      strategy/*.java
```

### Running the Game

```bash
# Run from the tic-tac-toe directory
java TicTacToeGame
```

### Game Flow

1. **Board Setup**: Choose board size (3-10)
2. **Player Configuration**:
   - Enter number of players
   - For each player:
     - Name
     - Symbol (unique character)
     - Type (Human/AI)
3. **Gameplay**: Players take turns making moves
4. **Game End**: Winner announced or draw declared
5. **Play Again**: Option to replay with same configuration

### Example Session

```
==================================================
        WELCOME TO TIC-TAC-TOE GAME
==================================================

Enter board size (3-10): 3

Enter number of players (default 2): 2

=== Player 1 Configuration ===
Enter player name: Alice
Enter player symbol (single character): X
Player type (1 = Human, 2 = AI): 1

=== Player 2 Configuration ===
Enter player name: Bot
Enter player symbol (single character): O
Player type (1 = Human, 2 = AI): 2

==================================================
              GAME START
==================================================

   0   1   2
  -------------
0 |   |   |   |
  -------------
1 |   |   |   |
  -------------
2 |   |   |   |
  -------------

Alice's turn (Symbol: X)
Enter row (0-2): 1
Enter column (0-2): 1

   0   1   2
  -------------
0 |   |   |   |
  -------------
1 |   | X |   |
  -------------
2 |   |   |   |
  -------------

Bot (AI) is thinking...
Bot chose position (0, 0)

   0   1   2
  -------------
0 | O |   |   |
  -------------
1 |   | X |   |
  -------------
2 |   |   |   |
  -------------
```

## Extensibility

### Adding New Player Strategies

```java
public class ExpertAIStrategy implements PlayerStrategy {
    @Override
    public Move makeMove(Player player, Board board) {
        // Implement minimax algorithm
        // Or alpha-beta pruning
        // Or machine learning model
    }
}
```

### Adding New Winning Conditions

```java
public class ConnectFourStrategy implements WinningStrategy {
    @Override
    public boolean checkWinner(Board board, Move lastMove) {
        // Check for 4-in-a-row in any direction
    }
}
```

### Adding Undo/Redo Functionality

The `GameState` already maintains move history:

```java
public void undoMove(GameState gameState) {
    List<Move> history = gameState.getMoveHistory();
    if (!history.isEmpty()) {
        Move lastMove = history.remove(history.size() - 1);
        lastMove.getCell().reset();
        gameState.switchPlayer(); // Go back to previous player
    }
}
```

### Adding Different Board Types

```java
public class HexagonalBoard extends Board {
    // Implement hexagonal grid logic
}
```


## Performance Considerations

- **Time Complexity**: O(N) for win checking, where N is board size
- **Space Complexity**: O(N²) for board storage
- **Optimizations**:
  - Only check affected row/column/diagonals after each move
  - Lazy evaluation of win conditions
  - Efficient empty cell tracking

## Code Quality

### Best Practices Demonstrated
✅ Meaningful variable and method names
✅ Comprehensive JavaDoc comments (recommended)
✅ Proper encapsulation (private fields, public interfaces)
✅ Immutability where appropriate (Move, Cell coordinates)
✅ Defensive copying in getters
✅ Input validation at boundaries
✅ Separation of concerns
✅ DRY (Don't Repeat Yourself)
✅ Clear error messages

## Contributing

When extending this codebase:
1. Follow existing naming conventions
2. Maintain SOLID principles
3. Add appropriate exception handling
4. Update this README with new features
5. Consider backward compatibility

## License

This is an educational project demonstrating low-level design principles.

## Author

Built as a practice for low level software design, machine coding interviews and portfolio purposes.

---

**Note**: This implementation prioritizes code quality, maintainability, and extensibility over brevity. It's designed to showcase professional software engineering practices.
