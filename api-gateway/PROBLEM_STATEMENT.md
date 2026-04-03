# API Gateway with Authentication

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement an API Gateway that handles authentication, rate limiting, and request routing.

---

## Requirements

### Must Have (Core)
- Register API endpoints with backend services
- API key authentication
- Route requests to appropriate backend
- Rate limiting per API key
- Request/Response logging
- Health check for backends

### Good to Have
- JWT token validation
- Request transformation (add headers)

### Not Expected
- Actual HTTP handling (simulate request/response)
- Load balancing
- Circuit breaker
- Caching

---

## Expected Input/Output

```
Input:
> register_backend users_service "http://localhost:8001"
> register_backend orders_service "http://localhost:8002"
> register_route GET /api/users users_service /users
> register_route POST /api/orders orders_service /orders
> generate_api_key client1 100 3600    # 100 requests/hour
> request GET /api/users --key API-KEY-001
> request POST /api/orders --key API-KEY-001 --body '{"item":"book"}'
> request GET /api/users --key INVALID-KEY
> logs client1

Output:
Backend "users_service" registered: http://localhost:8001
Backend "orders_service" registered: http://localhost:8002

Route registered: GET /api/users → users_service /users
Route registered: POST /api/orders → orders_service /orders

API Key generated for client1:
  Key: API-KEY-001
  Rate Limit: 100 requests/hour
  Expires: 3600s

Processing: GET /api/users
  Auth: API-KEY-001 ✓
  Rate Limit: 1/100 ✓
  Routing to: users_service /users
  Response: 200 OK
  Time: 45ms

Processing: POST /api/orders
  Auth: API-KEY-001 ✓
  Rate Limit: 2/100 ✓
  Body: {"item":"book"}
  Routing to: orders_service /orders
  Response: 201 Created
  Time: 62ms

Processing: GET /api/users
  Auth: INVALID-KEY ✗
  Response: 401 Unauthorized

Logs for client1:
  [10:30:00] GET /api/users → 200 (45ms)
  [10:30:05] POST /api/orders → 201 (62ms)
  Total requests: 2
  Remaining quota: 98/100
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles request flow |
| **OO Design** | 25% | Proper abstraction for components |
| **Design Patterns** | 20% | Chain of Responsibility, Proxy |
| **Code Quality** | 15% | Clean code, separation of concerns |
| **Extensibility** | 10% | Easy to add middleware |
| **Edge Cases** | 5% | Invalid routes, rate limit exceeded |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement JWT validation?
2. How would you add request/response transformation?
3. How would you implement API versioning?
4. How would you add support for WebSocket proxying?

### Reliability
5. How would you implement circuit breaker?
6. How would you handle backend failures?
7. How would you implement retry logic?

### Scalability
8. How would you scale the gateway horizontally?
9. How would you implement distributed rate limiting?
10. How would you add caching at the gateway level?

---

## Request Processing Pipeline

```
Request → Auth → RateLimit → Route → Transform → Backend → Response
            ↓        ↓          ↓
          401      429        404
```

---

## Component Interfaces

```
interface AuthHandler {
    AuthResult authenticate(Request request);
}

interface RateLimiter {
    boolean allowRequest(String clientId);
}

interface Router {
    Route findRoute(String method, String path);
}

interface Backend {
    Response forward(Request request);
    boolean isHealthy();
}
```

---

## Middleware Chain

```
class GatewayHandler {
    List<Middleware> middlewares;

    Response handle(Request request) {
        for (Middleware m : middlewares) {
            Result r = m.process(request);
            if (r.shouldStop()) return r.getResponse();
        }
        return routeToBackend(request);
    }
}

Middlewares:
1. LoggingMiddleware
2. AuthenticationMiddleware
3. RateLimitingMiddleware
4. TransformationMiddleware
```

---

## Hints for Candidate

- Use Chain of Responsibility for middleware pipeline
- Route matching can use simple string matching or regex
- API keys can be stored in a Map with metadata
- Consider a Request/Response object to pass through pipeline
