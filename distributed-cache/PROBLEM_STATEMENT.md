# Distributed Cache (LRU, LFU)

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement an in-memory cache with configurable eviction policies.

---

## Requirements

### Must Have (Core)
- Fixed capacity cache
- GET operation (return value, update for LRU/LFU)
- PUT operation (insert/update, evict if full)
- Eviction policies:
  - **LRU:** Least Recently Used
  - **LFU:** Least Frequently Used
- O(1) time complexity for both operations

### Good to Have
- TTL for cache entries
- Cache statistics (hits, misses, evictions)

### Not Expected
- Distributed caching
- Persistence
- Cache warming
- Thread safety

---

## Expected Input/Output

### LRU Example

```
Input:
> create_cache LRU 3
> put A 1
> put B 2
> put C 3
> get A
> put D 4
> get_all
> stats

Output:
Cache created: LRU with capacity 3

PUT A=1 (Cache: [A])
PUT B=2 (Cache: [B, A])
PUT C=3 (Cache: [C, B, A])
GET A → 1 (Cache: [A, C, B])
PUT D=4 → Evicted B (Cache: [D, A, C])

Cache contents: {A: 1, C: 3, D: 4}

Stats:
  Hits: 1
  Misses: 0
  Evictions: 1
  Size: 3/3
```

### LFU Example

```
Input:
> create_cache LFU 3
> put A 1
> put B 2
> put C 3
> get A
> get A
> get B
> put D 4
> get_all

Output:
Cache created: LFU with capacity 3

PUT A=1 (A freq=1)
PUT B=2 (B freq=1)
PUT C=3 (C freq=1)
GET A → 1 (A freq=2)
GET A → 1 (A freq=3)
GET B → 2 (B freq=2)
PUT D=4 → Evicted C (freq=1, least frequent)

Cache contents: {A: 1, B: 2, D: 4}
Frequencies: {A: 3, B: 2, D: 1}
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, correct eviction |
| **OO Design** | 25% | Proper abstraction for policies |
| **Design Patterns** | 20% | Strategy pattern for eviction |
| **Code Quality** | 15% | Clean code, O(1) operations |
| **Extensibility** | 10% | Easy to add new policies |
| **Edge Cases** | 5% | Full cache, duplicate keys |

---

## Follow-up Questions

### Design & Architecture
1. How do you achieve O(1) for LRU? What data structures?
2. How do you achieve O(1) for LFU? Explain the data structures.
3. How would you implement LFU tie-breaking (same frequency)?
4. How would you add a new eviction policy (like FIFO)?

### Optimization
5. How would you add TTL support efficiently?
6. How would you implement cache warming strategies?
7. How would you track cache hit rate over time?

### Distributed
8. How would you extend this to a distributed cache?
9. How would you handle cache consistency in distributed setup?
10. How would you implement write-through vs write-behind?

---

## Data Structures for O(1) Operations

### LRU Cache
```
HashMap<Key, Node> + Doubly Linked List

- HashMap: O(1) lookup
- DLL: O(1) move to front, O(1) remove from end
- Node contains: key, value, prev, next
```

### LFU Cache
```
HashMap<Key, Node> + HashMap<Frequency, DLL> + minFrequency

- keyMap: key → node
- freqMap: frequency → doubly linked list of nodes
- minFreq: track minimum frequency for eviction
- Node contains: key, value, frequency
```

---

## Hints for Candidate

- LRU: Doubly linked list with HashMap for O(1)
- LFU: Two HashMaps - one for key→node, one for freq→list
- Consider a Node class with key, value, and metadata
- Strategy pattern allows swapping eviction policies
