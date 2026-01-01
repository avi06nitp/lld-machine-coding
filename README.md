# Low-Level Design (LLD) Machine Coding

A comprehensive collection of 25+ object-oriented design problems and machine coding implementations for technical interview preparation. Each project demonstrates clean architecture, design patterns, and SOLID principles.

## Tech Stack
- **Language:** Java
- **Focus:** Design Patterns, Clean Code, System Design

## Project Overview

This repository contains implementations ranging from beginner to advanced levels, covering various real-world system design scenarios commonly asked in technical interviews at top tech companies.

## Projects by Difficulty Level

### Beginner Level

| # | Project Name | Design Patterns Used |
|---|--------------|---------------------|
| 1 | [Tic-Tac-Toe](./tic-tac-toe) | MVC, Singleton for Game Manager |
| 2 | [Snake & Ladder](./snake-and-ladder) | Singleton for Dice, Factory for Board Creation |
| 3 | Parking Lot System | Factory for Vehicle, Singleton for Parking Lot |
| 4 | Cab Booking System | Observer for Ride Matching, Factory for Vehicle |
| 5 | Splitwise Clone | Strategy for Expense Split, Observer for Notifications |

### Intermediate Level

| # | Project Name | Design Patterns Used |
|---|--------------|---------------------|
| 6 | Rate Limiter | Token Bucket, Sliding Window - Strategy Pattern |
| 7 | Elevator System | Command for Requests, Observer for Floor Updates |
| 8 | Library Management System | Factory for Books, Singleton for Database Access |
| 9 | Hotel Booking System | Strategy for Pricing, Observer for Availability Updates |
| 10 | In-Memory Key-Value Store (Redis Clone) | LRU Cache - Strategy, Singleton for Storage |
| 11 | Message Queue System (RabbitMQ Clone) | Publisher-Subscriber, Observer Pattern |
| 12 | URL Shortener (Bit.ly Clone) | Factory for Generating Short Links, Caching |
| 13 | Stock Trading Platform | Observer for Price Updates, Factory for Order Matching |
| 14 | Search Autocomplete System | Trie Data Structure, Observer for Query Logging |
| 15 | Food Delivery App (Zomato Clone) | Strategy for Delivery Assignment, Observer for Order Updates |

### Advanced Level

| # | Project Name | Design Patterns Used |
|---|--------------|---------------------|
| 16 | Distributed Cache (LRU, LFU) | LRU Cache, Singleton for Cache Store |
| 17 | Real-time Chat Application (WhatsApp Clone) | WebSockets, Observer for Message Events |
| 18 | Event Ticket Booking System | Concurrency Control, CQRS for Read Optimization |
| 19 | Multiplayer Online Chess Game | Command for Moves, Observer for Game State Updates |
| 20 | Blockchain-based Ledger System | Merkle Tree, Immutable Transactions, Factory for Blocks |
| 21 | AI-Based Code Review System | Command for Code Analysis, Strategy for Rule Checking |
| 22 | Payment Gateway System | State Pattern for Payment Lifecycle, Observer for Transactions |
| 23 | Ride-Sharing App (Uber Clone) | Graph Algorithm for Route Optimization, Observer for Ride Matching |
| 24 | Online Code Compiler | Command for Code Execution, Factory for Language Selection |
| 25 | API Gateway with Authentication | Singleton for Authentication Manager, Proxy for Routing Requests |

## Design Patterns Covered

This repository demonstrates practical implementation of various design patterns:

### Creational Patterns
- **Singleton:** Game Manager, Dice, Parking Lot, Database Access, Cache Store, Authentication Manager
- **Factory:** Vehicle Creation, Board Creation, Book Creation, Order Matching, Block Creation, Language Selection

### Structural Patterns
- **Proxy:** Routing Requests in API Gateway
- **MVC:** Tic-Tac-Toe architecture

### Behavioral Patterns
- **Observer:** Ride Matching, Notifications, Floor Updates, Availability Updates, Price Updates, Order Updates, Message Events, Game State Updates, Transactions
- **Strategy:** Expense Split, Rate Limiting, Pricing, Delivery Assignment, Cache Eviction, Rule Checking
- **Command:** Elevator Requests, Chess Moves, Code Analysis, Code Execution
- **State:** Payment Lifecycle

### Architectural Patterns
- **Publisher-Subscriber:** Message Queue System
- **CQRS:** Event Ticket Booking System (Read Optimization)

### Data Structures
- **LRU/LFU Cache:** In-Memory Store, Distributed Cache
- **Trie:** Search Autocomplete
- **Merkle Tree:** Blockchain Ledger
- **Graph Algorithms:** Route Optimization in Ride-Sharing

## Repository Structure

```
lld-machine-coding/
├── tic-tac-toe/
├── snake-and-ladder/
├── parking-lot/
├── cab-booking/
├── splitwise/
└── ... (more projects)
```

Each project directory contains:
- Source code with clean architecture
- Design pattern implementations
- README with problem statement and approach
- Class diagrams (where applicable)

## How to Run

Each project is self-contained. Navigate to the specific project directory and follow the instructions in its README.

```bash
cd <project-name>
# Follow project-specific instructions
```

## Learning Objectives

- Master common design patterns through practical implementation
- Understand SOLID principles in real-world scenarios
- Practice machine coding for technical interviews
- Learn to design scalable and maintainable systems
- Improve object-oriented programming skills

## Key Concepts

- **SOLID Principles:** Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion
- **Clean Code:** Readable, maintainable, and testable code
- **Design Patterns:** Gang of Four patterns and modern architectural patterns
- **System Design:** Scalability, concurrency, and performance considerations

## Interview Preparation Tips

1. **Understand the Problem:** Clarify requirements before coding
2. **Identify Core Entities:** Define classes and relationships
3. **Apply Design Patterns:** Choose appropriate patterns for the problem
4. **Follow SOLID Principles:** Keep code modular and extensible
5. **Handle Edge Cases:** Consider concurrency, null checks, and validations
6. **Test Your Code:** Write clean, working code with proper error handling

## Contributing

Contributions are welcome! Feel free to:
- Add new machine coding problems
- Improve existing implementations
- Add test cases
- Enhance documentation

## Resources

- Design Patterns: Elements of Reusable Object-Oriented Software (Gang of Four)
- Head First Design Patterns
- Refactoring: Improving the Design of Existing Code
- Clean Code by Robert C. Martin

## License

This project is for educational purposes and interview preparation.

---

**Happy Coding!** Practice these problems to ace your machine coding rounds at top tech companies.