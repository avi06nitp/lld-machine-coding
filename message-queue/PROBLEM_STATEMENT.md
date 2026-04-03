# Message Queue System (RabbitMQ Clone)

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement an in-memory message queue system with support for multiple queues and consumers.

---

## Requirements

### Must Have (Core)
- Create/delete queues
- Publish message to queue
- Subscribe consumer to queue
- Round-robin delivery to multiple consumers
- Acknowledge message (mark as processed)
- Requeue on timeout (if not acknowledged)

### Good to Have
- Dead letter queue (after N retries)
- Message priority

### Not Expected
- Persistence
- Exchanges and routing keys
- Clustering
- Transactions

---

## Expected Input/Output

```
Input:
> create_queue orders
> create_queue notifications
> subscribe orders consumer1
> subscribe orders consumer2
> publish orders "Order #1001"
> publish orders "Order #1002"
> publish orders "Order #1003"
> consume consumer1
> consume consumer2
> consume consumer1
> ack consumer1 msg-001
> status orders

Output:
Queue "orders" created
Queue "notifications" created

consumer1 subscribed to "orders"
consumer2 subscribed to "orders"

Message published to "orders": msg-001
Message published to "orders": msg-002
Message published to "orders": msg-003

consumer1 received: msg-001 "Order #1001"
consumer2 received: msg-002 "Order #1002"
consumer1 received: msg-003 "Order #1003"

msg-001 acknowledged by consumer1

Queue "orders" status:
  Total messages: 3
  Pending: 2 (msg-002, msg-003)
  Acknowledged: 1
  Consumers: 2 (consumer1, consumer2)
```

### Message Lifecycle

```
Message States:
1. QUEUED - In queue, waiting to be consumed
2. DELIVERED - Sent to consumer, waiting for ack
3. ACKNOWLEDGED - Successfully processed
4. REQUEUED - Timeout, sent back to queue

Flow:
PUBLISH → QUEUED → CONSUME → DELIVERED → ACK → ACKNOWLEDGED
                                      ↓
                              TIMEOUT → REQUEUED → QUEUED
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, correct message flow |
| **OO Design** | 25% | Proper modeling of Queue, Message, Consumer |
| **Design Patterns** | 20% | Observer, Factory |
| **Code Quality** | 15% | Clean code, thread safety considerations |
| **Extensibility** | 10% | Easy to add features |
| **Edge Cases** | 5% | Consumer disconnect, empty queue |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement message priority?
2. How would you handle consumer disconnection?
3. How would you implement pub/sub (fan-out)?
4. How would you add routing keys (topic-based)?

### Reliability
5. How would you ensure at-least-once delivery?
6. How would you handle poison messages (always failing)?
7. How would you implement exactly-once delivery?

### Scalability
8. How would you scale to millions of messages?
9. How would you add persistence for durability?
10. How would you implement partitioning?

---

## Key Concepts

### Delivery Semantics

| Type | Description | Trade-off |
|------|-------------|-----------|
| At-most-once | Fire and forget | May lose messages |
| At-least-once | Retry until ack | May have duplicates |
| Exactly-once | Track processed | Complex, slower |

### Consumer Patterns

- **Competing Consumers:** Multiple consumers, round-robin
- **Fan-out:** Message goes to all subscribers
- **Topic-based:** Route by pattern matching

---

## Hints for Candidate

- Queue: LinkedList for messages, List for consumers
- Track which consumer received which message
- Consider timeout thread for unacked messages
- Round-robin: use index % consumers.size()
