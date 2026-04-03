# Search Autocomplete System

**Difficulty:** ⭐⭐⭐☆☆ (Medium)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a search autocomplete system that suggests queries based on prefix matching and popularity.

---

## Requirements

### Must Have (Core)
- Add search terms with frequency
- Get top K suggestions for prefix (sorted by frequency)
- Update frequency when term is searched
- Case-insensitive matching

### Good to Have
- Recent searches per user
- Trending searches (time-decayed popularity)

### Not Expected
- Spell correction
- Personalized suggestions
- Machine learning based ranking
- Fuzzy matching

---

## Expected Input/Output

```
Input:
> add_term "amazon" 100000
> add_term "amazon prime" 50000
> add_term "amazon web services" 30000
> add_term "apple" 80000
> add_term "apple watch" 40000
> add_term "application" 20000
> search "am" 3
> search "app" 5
> record_search "apple"
> record_search "apple"
> search "app" 3

Output:
Term "amazon" added (frequency: 100000)
Term "amazon prime" added (frequency: 50000)
Term "amazon web services" added (frequency: 30000)
Term "apple" added (frequency: 80000)
Term "apple watch" added (frequency: 40000)
Term "application" added (frequency: 20000)

Top 3 suggestions for "am":
1. amazon (100000)
2. amazon prime (50000)
3. amazon web services (30000)

Top 5 suggestions for "app":
1. apple (80000)
2. apple watch (40000)
3. application (20000)

Search recorded: "apple" (frequency: 80001)
Search recorded: "apple" (frequency: 80002)

Top 3 suggestions for "app":
1. apple (80002)
2. apple watch (40000)
3. application (20000)
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, correct suggestions |
| **OO Design** | 25% | Proper use of data structures |
| **Design Patterns** | 20% | Trie usage, Strategy for ranking |
| **Code Quality** | 15% | Clean code, efficient implementation |
| **Extensibility** | 10% | Easy to change ranking |
| **Edge Cases** | 5% | Empty prefix, no matches |

---

## Follow-up Questions

### Design & Architecture
1. What data structure would you use for efficient prefix search?
2. How would you implement trending searches?
3. How would you handle multi-word queries?
4. How would you add recent searches per user?

### Optimization
5. How would you optimize for millions of search terms?
6. What's the time complexity of your search operation?
7. How would you handle memory constraints?

### Scalability
8. How would you distribute this across multiple servers?
9. How would you handle real-time updates?
10. How would you implement spell correction?

---

## Data Structures

### Trie (Prefix Tree)
```
        root
       /    \
      a      ...
     / \
    m   p
    |   |
    a   p
    |   |
    z   l
    |   |
    o   e (apple: 80000)
    |
    n (amazon: 100000)
```

### Trie + Heap
- Each node stores top K suggestions for that prefix
- O(1) lookup, O(k log k) update

### HashMap + Sorting
- Map<prefix, List<Term>>
- Simpler but less efficient
- O(n log n) for each search

---

## Hints for Candidate

- Trie is ideal for prefix matching
- Store frequency at each node or at word end
- Consider storing pre-computed top K at each node
- Case-insensitive: convert to lowercase before operations
