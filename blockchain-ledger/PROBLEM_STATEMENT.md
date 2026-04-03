# Blockchain-based Ledger System

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a simplified blockchain for maintaining an immutable transaction ledger.

---

## Requirements

### Must Have (Core)
- Create genesis block
- Add transaction (sender, receiver, amount)
- Mine block (simple proof-of-work with configurable difficulty)
- Validate chain integrity
- View chain/block details

### Good to Have
- Transaction validation (sufficient balance)
- Mining reward

### Not Expected
- Distributed consensus
- Merkle trees
- Smart contracts
- Networking / P2P

---

## Expected Input/Output

```
Input:
> init_chain 2    # difficulty = 2 (hash must start with "00")
> add_transaction "Alice" "Bob" 50
> add_transaction "Bob" "Charlie" 25
> mine_block "Miner1"
> add_transaction "Charlie" "Alice" 10
> mine_block "Miner1"
> view_chain
> validate_chain

Output:
Blockchain initialized with difficulty 2
Genesis block created (Block #0)

Transaction added: Alice → Bob: 50
Transaction added: Bob → Charlie: 25

Mining block #1...
Nonce: 0 → Hash: a1b2c3...
Nonce: 1 → Hash: 9f8e7d...
...
Nonce: 234 → Hash: 00f3d2...
Block #1 mined!
  Transactions: 2
  Nonce: 234
  Hash: 00f3d2...
  Mining reward: 10 to Miner1

Transaction added: Charlie → Alice: 10

Mining block #2...
Block #2 mined!
  Transactions: 1
  Nonce: 567
  Hash: 00a8b1...
  Mining reward: 10 to Miner1

Blockchain:
Block #0 (Genesis)
  Hash: 00000...
  Transactions: 0

Block #1
  Previous: 00000...
  Hash: 00f3d2...
  Transactions:
    - Alice → Bob: 50
    - Bob → Charlie: 25
    - REWARD: Miner1: 10
  Nonce: 234

Block #2
  Previous: 00f3d2...
  Hash: 00a8b1...
  Transactions:
    - Charlie → Alice: 10
    - REWARD: Miner1: 10
  Nonce: 567

Chain validation: VALID
All blocks properly linked.
All hashes valid for difficulty 2.
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, correct hashing |
| **OO Design** | 25% | Proper modeling of Block, Transaction |
| **Design Patterns** | 20% | Chain of Responsibility, Builder |
| **Code Quality** | 15% | Clean code, correct crypto usage |
| **Extensibility** | 10% | Easy to modify consensus |
| **Edge Cases** | 5% | Empty chain, invalid transactions |

---

## Follow-up Questions

### Design & Architecture
1. How does proof-of-work prevent tampering?
2. How would you implement a merkle tree for transactions?
3. How would you handle transaction validation (sufficient balance)?
4. How would you implement different consensus mechanisms?

### Security
5. What happens if someone tries to modify a past transaction?
6. How would you prevent double-spending?
7. Why is the previous hash included in each block?

### Scalability
8. How would you implement sharding?
9. How would you add smart contract support?
10. How would you handle chain forks?

---

## Block Structure

```
Block:
  - index: Block number in chain
  - timestamp: When block was created
  - transactions: List of transactions
  - previousHash: Hash of previous block
  - nonce: Number used for mining
  - hash: SHA-256(index + timestamp + transactions + previousHash + nonce)

Genesis Block:
  - index: 0
  - previousHash: "0" or null
  - transactions: []
```

---

## Proof of Work

```
Difficulty = 2 means hash must start with "00"
Difficulty = 3 means hash must start with "000"

Mining:
1. nonce = 0
2. hash = SHA256(block data + nonce)
3. if hash starts with required zeros → success
4. else nonce++, goto step 2
```

---

## Hints for Candidate

- Use SHA-256 for hashing (java.security.MessageDigest)
- Block contains list of transactions + metadata
- Chain is list of blocks where each references previous
- Validation: recalculate each hash and verify links
