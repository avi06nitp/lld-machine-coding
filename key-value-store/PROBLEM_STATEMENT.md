# In-Memory Key-Value Store (Redis Clone)

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement an in-memory key-value store with support for different data types and expiration.

---

## Requirements

### Must Have (Core)
- Basic operations: GET, SET, DELETE
- Data types: String, List (LPUSH, RPUSH, LPOP, RPOP)
- Key expiration (SET with TTL, EXPIRE command)
- EXISTS, KEYS (pattern matching with *)

### Good to Have
- INCR/DECR for integers
- Hash data type (HSET, HGET)

### Not Expected
- Persistence (RDB/AOF)
- Pub/Sub
- Transactions (MULTI/EXEC)
- Cluster mode
- Lua scripting

---

## Expected Input/Output

```
Input:
> SET name "Alice"
> GET name
> SET counter 10
> INCR counter
> SET session abc123 EX 60    # Expires in 60 seconds
> LPUSH queue task1
> LPUSH queue task2
> RPUSH queue task3
> LPOP queue
> KEYS *
> EXPIRE name 30
> TTL name
> EXISTS name
> DELETE name
> EXISTS name

Output:
OK

"Alice"

OK

11

OK

1    # Length after push

2

3

"task2"    # LPOP returns from left

["name", "counter", "session", "queue"]

OK

30    # seconds remaining

1    # exists

OK

0    # doesn't exist
```

### List Operations Example

```
Initial: queue = []

LPUSH queue task1  → queue = [task1]
LPUSH queue task2  → queue = [task2, task1]
RPUSH queue task3  → queue = [task2, task1, task3]
LPOP queue         → returns "task2", queue = [task1, task3]
RPOP queue         → returns "task3", queue = [task1]
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, correct behavior |
| **OO Design** | 25% | Proper abstraction for data types |
| **Design Patterns** | 20% | Strategy for data types, Factory |
| **Code Quality** | 15% | Clean code, efficient implementation |
| **Extensibility** | 10% | Easy to add new data types |
| **Edge Cases** | 5% | Expired keys, type mismatches |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement key expiration efficiently?
2. How would you add support for Sets (SADD, SMEMBERS)?
3. How would you implement pattern matching in KEYS?
4. How would you add Hash data type?

### Optimization
5. How would you handle memory limits (eviction policies)?
6. What data structure would you use for efficient TTL management?
7. How would you implement lazy vs active expiration?

### Advanced
8. How would you implement transactions (MULTI/EXEC)?
9. How would you add persistence without blocking operations?
10. How would you implement Pub/Sub?

---

## Data Structures to Consider

| Operation | Data Structure | Time Complexity |
|-----------|---------------|-----------------|
| GET/SET | HashMap | O(1) |
| List ops | LinkedList or Deque | O(1) |
| TTL tracking | Min-Heap or Sorted Set | O(log n) |
| Pattern matching | Iteration + regex | O(n) |

---

## Hints for Candidate

- Use a Map<String, Object> for storage, with type checking
- Consider a separate structure for tracking TTLs
- Lazy expiration: check TTL on access
- Active expiration: background cleanup of expired keys
- KEYS * should filter out expired keys
