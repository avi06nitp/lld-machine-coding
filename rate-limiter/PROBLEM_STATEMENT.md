# Rate Limiter

**Difficulty:** ⭐⭐⭐☆☆ (Easy-Medium)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a rate limiter that controls the number of requests a client can make to a service within a defined time window, supporting multiple algorithms and per-client configuration.

---

## Requirements

### Must Have (Core)
- Register clients with individual rate limit rules (max requests per window)
- Support at least two algorithms: Token Bucket and Sliding Window Log
- Allow or deny incoming requests based on the active algorithm
- Track request counts and timestamps per client
- Return remaining quota and reset time on each request

### Good to Have
- Global fallback rate limit for unregistered clients
- Burst allowance on top of base rate (Token Bucket)
- Per-endpoint rate limit rules

### Not Expected
- Distributed rate limiting across nodes
- Persistent storage
- HTTP middleware integration
- Redis/external cache integration

---

## Expected Input/Output

```
Input:
> set_algorithm TOKEN_BUCKET
> register_client C1 5 60        # 5 requests per 60 seconds
> register_client C2 3 60
> request C1 req-001
> request C1 req-002
> request C1 req-003
> request C1 req-004
> request C1 req-005
> request C1 req-006             # exceeds limit
> request C2 req-007
> set_algorithm SLIDING_WINDOW
> request C1 req-008             # window has not reset yet

Output:
Algorithm set to: TOKEN_BUCKET

Client C1 registered: 5 requests / 60s
Client C2 registered: 3 requests / 60s

ALLOWED  | Client: C1 | Request: req-001 | Remaining: 4 | Resets in: 60s
ALLOWED  | Client: C1 | Request: req-002 | Remaining: 3 | Resets in: 57s
ALLOWED  | Client: C1 | Request: req-003 | Remaining: 2 | Resets in: 54s
ALLOWED  | Client: C1 | Request: req-004 | Remaining: 1 | Resets in: 51s
ALLOWED  | Client: C1 | Request: req-005 | Remaining: 0 | Resets in: 48s
DENIED   | Client: C1 | Request: req-006 | Reason: Rate limit exceeded | Resets in: 48s

ALLOWED  | Client: C2 | Request: req-007 | Remaining: 2 | Resets in: 60s

Algorithm switched to: SLIDING_WINDOW

DENIED   | Client: C1 | Request: req-008 | Reason: Rate limit exceeded | Resets in: 43s
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, correctly allows/denies requests |
| **OO Design** | 25% | Clean abstraction for algorithms, clients, rules |
| **Design Patterns** | 20% | Strategy for algorithm selection, Singleton for limiter |
| **Code Quality** | 15% | Readable, well-named, no magic numbers |
| **Extensibility** | 10% | Easy to add a new algorithm (e.g., Fixed Window, Leaky Bucket) |
| **Edge Cases** | 5% | Unregistered client, zero limit, rapid burst requests |

---

## Follow-up Questions

### Design & Architecture
1. How does Token Bucket differ from Leaky Bucket — when would you choose one over the other?
2. How would you implement a Fixed Window Counter algorithm and what are its drawbacks?
3. How would you support per-endpoint limits in addition to per-client limits?
4. How would you handle burst traffic gracefully without hard-denying all excess requests?

### Concurrency
5. What race conditions can occur when two threads check and update the token count simultaneously?
6. How would you make this rate limiter thread-safe?
7. Would you use synchronized blocks, ReentrantLock, or atomic variables — why?

### Scalability
8. How would you extend this to a distributed rate limiter shared across multiple service instances?
9. How would you use Redis to implement a distributed sliding window?
10. How would you handle clock skew across nodes in a distributed setup?

---

## Algorithm Overview

```
Token Bucket:
  - Bucket holds up to `capacity` tokens
  - Tokens refill at a fixed rate
  - Each request consumes one token
  - Allows bursting up to bucket capacity

Sliding Window Log:
  - Store timestamp of every request
  - On each request, remove timestamps older than the window
  - If remaining log size < limit → ALLOW, else → DENY
  - Precise but memory-intensive

Fixed Window Counter (bonus):
  - Divide time into fixed windows (e.g., every 60s)
  - Count requests per window
  - Simple but can allow 2x burst at window boundaries
```

---

## Core Interfaces

```java
interface RateLimitAlgorithm {
    RateLimitResult tryAcquire(String clientId, ClientRule rule);
}

interface RateLimiter {
    void registerClient(String clientId, int maxRequests, int windowSeconds);
    RateLimitResult handleRequest(String clientId, String requestId);
    void setAlgorithm(AlgorithmType type);
}

class RateLimitResult {
    boolean allowed;
    int remaining;
    long resetsInSeconds;
    String reason;
}
```

---

## Hints for Candidate

- Use Strategy pattern to swap between Token Bucket and Sliding Window at runtime
- Keep client state (tokens, timestamps) separate from the algorithm logic
- For Token Bucket, refill tokens lazily on each request based on elapsed time
- For Sliding Window, use a Deque to efficiently remove expired timestamps
- Consider a ClientRule value object to hold limit and window configuration