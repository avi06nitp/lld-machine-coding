# Blockchain-based Ledger System

A complete implementation of a blockchain ledger demonstrating **Merkle Tree**, **Immutable Transactions**, and the **Factory** and **Strategy** design patterns.

## Problem Statement

Design a blockchain ledger system that:
- Records financial transactions immutably
- Groups transactions into blocks linked by cryptographic hashes
- Validates chain integrity using Merkle Trees
- Supports pluggable mining strategies (Proof-of-Work or simple)
- Queries balances and transaction history by address

## Design Patterns Used

| Pattern | Where Used | Why |
|---------|-----------|-----|
| **Factory** | `BlockFactory` creates `Block` instances | Decouples block construction (including mining) from callers |
| **Strategy** | `MiningStrategy` → `ProofOfWorkStrategy`, `SimpleMiningStrategy` | Swap mining algorithms without changing the factory or service |
| **Immutable Value Objects** | `Transaction`, `Block` | Prevents tampering once data is committed |

## Architecture

```
blockchain-ledger/
├── enums/
│   ├── TransactionStatus.java     # PENDING, CONFIRMED, FAILED
│   └── BlockStatus.java           # PENDING, MINED, INVALID
├── exception/
│   ├── BlockchainException.java   # Base exception
│   ├── InvalidTransactionException.java
│   └── InvalidBlockException.java
├── factory/
│   └── BlockFactory.java          # Creates mined blocks via MiningStrategy
├── model/
│   ├── Transaction.java           # Immutable transaction (SHA-256 hash)
│   ├── MerkleTree.java            # Binary hash tree over transactions
│   ├── Block.java                 # Immutable block (index, prevHash, txs, nonce, hash)
│   └── Blockchain.java            # Append-only chain of blocks
├── service/
│   ├── BlockchainService.java     # Transaction pool, mining, balance & history queries
│   └── ValidationService.java    # Chain and block integrity checks
├── strategy/
│   ├── MiningStrategy.java        # Interface: mine(index, prevHash, merkleRoot) → nonce
│   ├── ProofOfWorkStrategy.java   # Hash must start with N zeros (configurable difficulty)
│   └── SimpleMiningStrategy.java  # Nonce = 0, instant (for testing/demo)
├── util/
│   └── HashUtil.java              # SHA-256 utility
└── BlockchainLedger.java          # Main demo entry point
```

## Core Concepts

### Immutable Transactions
Each `Transaction` is created with sender, receiver, and amount. Its SHA-256 hash is computed at construction time and never changes. Fields are final and there are no setters.

### Merkle Tree
The `MerkleTree` hashes all transaction hashes pairwise up the tree until a single **Merkle Root** remains. This root is stored in the block header:
- Any change to a transaction changes its hash → changes the Merkle root → changes the block hash → breaks the chain.

### Block Linking
Each block stores the `hash` of the previous block. Tampering with any historical block invalidates every subsequent block's `previousHash`, making tampering immediately detectable.

### Factory + Strategy for Mining
`BlockFactory` accepts a `MiningStrategy` and delegates nonce computation to it:
- `SimpleMiningStrategy` — nonce = 0, instant (for demos)
- `ProofOfWorkStrategy(difficulty)` — brute-forces until `sha256(data)` starts with `difficulty` leading zeros

## How to Run

```bash
cd blockchain-ledger

# Compile all sources
javac -d out $(find . -name "*.java")

# Run the demo
java -cp out BlockchainLedger
```

## Sample Output

```
=== Blockchain Ledger System Demo ===

Genesis block created: Block{index=0, hash='...', prevHash='000000000000', transactions=0, nonce=0}

--- Adding Transactions ---
Transaction added: Transaction{id='...', sender='Alice', receiver='Bob', amount=50.00, ...}
Transaction added: Transaction{id='...', sender='Bob', receiver='Charlie', amount=20.00, ...}
Transaction added: Transaction{id='...', sender='Charlie', receiver='Alice', amount=10.00, ...}
Mining block 1 with 3 transaction(s)...
Block mined: Block{index=1, ...}

--- Balances ---
Alice: -55.00
Bob:   35.00
Charlie: 10.00
Dave:   10.00

--- Chain Validation ---
Chain valid: true
```

## SOLID Principles Applied

- **SRP**: `Transaction` holds data, `MerkleTree` computes the root, `BlockFactory` creates blocks, `BlockchainService` manages workflow, `ValidationService` validates.
- **OCP**: New mining algorithms can be added by implementing `MiningStrategy` without touching existing classes.
- **LSP**: `ProofOfWorkStrategy` and `SimpleMiningStrategy` are interchangeable wherever `MiningStrategy` is expected.
- **DIP**: `BlockchainService` depends on `BlockFactory` and `ValidationService` abstractions, not on concrete mining logic.
