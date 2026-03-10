# Distributed Cache (LRU, LFU)

A thread-safe, sharded distributed cache implementation in Java demonstrating LRU and LFU eviction policies using clean OOP design.

## Problem Statement

Design a distributed cache system that:
- Supports **LRU** (Least Recently Used) and **LFU** (Least Frequently Used) eviction policies
- Is **thread-safe** for concurrent access
- Scales horizontally via **sharding** across multiple cache nodes
- Exposes a clean interface that hides eviction complexity from callers

## Design Patterns Used

| Pattern   | Usage |
|-----------|-------|
| **Strategy** | `EvictionStrategy` interface — swap LRU/LFU without changing cache code |
| **Factory** | `CacheFactory` — decouple object creation from usage |
| **Singleton** | Each `LocalCache` shard manages its own state; `DistributedCache` owns the shards |

## Architecture

```
DistributedCache
├── Shard 0: LocalCache (EvictionStrategy)
├── Shard 1: LocalCache (EvictionStrategy)
└── Shard N: LocalCache (EvictionStrategy)
```

- **`Cache<K, V>`** — top-level interface (put, get, delete, size, clear)
- **`LocalCache`** — single-node cache; thread-safe via `ReadWriteLock`
- **`DistributedCache`** — shards keys across N `LocalCache` instances using `key.hashCode() % numShards`
- **`EvictionStrategy`** — pluggable eviction algorithm; injected into `LocalCache`
- **`LRUEvictionStrategy`** — O(1) using `HashMap` + `DoublyLinkedList`
- **`LFUEvictionStrategy`** — O(1) using `HashMap` + frequency-bucketed `DoublyLinkedList`

## Project Structure

```
distributed-cache/
├── DistributedCacheDemo.java       # Entry point / demo
├── cache/
│   ├── Cache.java                  # Core cache interface
│   ├── LocalCache.java             # Thread-safe single-node cache
│   └── DistributedCache.java       # Sharded distributed cache
├── strategy/
│   ├── EvictionStrategy.java       # Strategy interface
│   ├── LRUEvictionStrategy.java    # LRU implementation
│   └── LFUEvictionStrategy.java    # LFU implementation
├── factory/
│   └── CacheFactory.java           # Factory for cache/strategy creation
├── model/
│   ├── CacheEntry.java             # Doubly linked list node
│   └── DoublyLinkedList.java       # Doubly linked list with sentinel nodes
├── enums/
│   └── EvictionPolicy.java         # LRU / LFU enum
├── exception/
│   └── CacheException.java         # Domain exception
└── test/
    ├── LRUCacheTest.java           # LRU unit tests
    ├── LFUCacheTest.java           # LFU unit tests
    └── DistributedCacheTest.java   # Distributed cache tests
```

## How to Run

### Compile and run demo
```bash
cd distributed-cache
mkdir -p out
javac -d out $(find . -name "*.java")
java -cp out DistributedCacheDemo
```

### Run tests
```bash
java -ea -cp out test.LRUCacheTest
java -ea -cp out test.LFUCacheTest
java -ea -cp out test.DistributedCacheTest
```

## Eviction Policy Details

### LRU (Least Recently Used)
- Evicts the entry that was **accessed least recently**
- Implementation: `HashMap` (O(1) lookup) + `DoublyLinkedList` (O(1) reorder)
- Head = MRU, Tail = LRU candidate

### LFU (Least Frequently Used)
- Evicts the entry with the **lowest access count**
- Tie-breaking: among entries with equal frequency, evicts the **least recently used**
- Implementation: `entryMap` (key → node) + `freqMap` (frequency → doubly linked list) + `minFreq` tracker
- All operations O(1)

## Key Design Decisions

1. **Strategy pattern for eviction**: `LocalCache` knows nothing about eviction logic. This makes it trivial to add new policies (e.g., FIFO, Random) without modifying cache code.

2. **ReadWriteLock in LocalCache**: Multiple threads can read concurrently; writes are exclusive. This gives better throughput than a plain `synchronized` block.

3. **Per-shard eviction state**: Each shard has its own strategy instance. This avoids lock contention across shards — shard 0 and shard 1 never block each other.

4. **Absolute hashCode for sharding**: `Math.abs(key.hashCode() % numShards)` guards against negative hashCodes returning negative shard indices.
