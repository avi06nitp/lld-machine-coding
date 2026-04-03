# URL Shortener (Bit.ly Clone)

**Difficulty:** ⭐⭐⭐☆☆ (Medium)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a URL shortening service that generates short URLs and tracks analytics.

---

## Requirements

### Must Have (Core)
- Shorten URL → return short code
- Redirect short URL → return original URL
- Custom short codes (optional user-provided alias)
- Link expiration (optional TTL)
- Click count per short URL

### Good to Have
- User accounts with link management
- Basic analytics (clicks per day)

### Not Expected
- QR code generation
- Geo-analytics
- A/B testing for links
- Real HTTP server

---

## Expected Input/Output

```
Input:
> shorten "https://example.com/very/long/url/path"
> shorten "https://google.com" --alias "goog"
> shorten "https://temp.com" --expires 3600
> resolve "abc123"
> resolve "goog"
> click "abc123"
> click "abc123"
> stats "abc123"
> list_all

Output:
Shortened: https://example.com/very/long/url/path
Short URL: http://short.ly/abc123

Shortened: https://google.com
Short URL: http://short.ly/goog (custom alias)

Shortened: https://temp.com
Short URL: http://short.ly/xyz789
Expires in: 3600 seconds

Resolved: http://short.ly/abc123
Original: https://example.com/very/long/url/path

Resolved: http://short.ly/goog
Original: https://google.com

Click recorded for abc123 (Count: 1)
Click recorded for abc123 (Count: 2)

Stats for abc123:
  Original URL: https://example.com/very/long/url/path
  Created: 2024-01-15 10:30:00
  Total clicks: 2
  Expires: Never

All URLs:
1. abc123 → https://example.com/very/long/url/path (2 clicks)
2. goog → https://google.com (0 clicks)
3. xyz789 → https://temp.com (0 clicks) [Expires in 3540s]
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, correct shortening |
| **OO Design** | 25% | Proper modeling of URL, ShortLink |
| **Design Patterns** | 20% | Factory, Strategy for encoding |
| **Code Quality** | 15% | Clean code, unique code generation |
| **Extensibility** | 10% | Easy to add features |
| **Edge Cases** | 5% | Collision, expired links |

---

## Follow-up Questions

### Design & Architecture
1. How would you generate unique short codes? What's the collision probability?
2. How would you handle custom alias conflicts?
3. How would you implement rate limiting?
4. How would you add user authentication?

### Scalability
5. How would you handle 1 billion URLs?
6. How would you design the database schema?
7. How would you handle high read traffic?

### Advanced
8. How would you implement link preview?
9. How would you detect and block malicious URLs?
10. How would you implement analytics dashboard?

---

## Short Code Generation Strategies

### Base62 Encoding
- Characters: a-z, A-Z, 0-9 (62 chars)
- 6 chars = 62^6 = ~56 billion combinations
- 7 chars = 62^7 = ~3.5 trillion combinations

### Counter + Base62
```
counter = 1000000
shortCode = toBase62(counter)  # "4c92"
```

### Random Generation
```
shortCode = randomAlphanumeric(6)  # "xK9mPq"
// Check for collision before saving
```

### Hash-based
```
hash = MD5(originalUrl)
shortCode = hash.substring(0, 6)
// May need collision handling
```

---

## Hints for Candidate

- Store: Map<shortCode, UrlMapping>
- UrlMapping: originalUrl, createdAt, expiresAt, clickCount
- Generate short code using counter + base62 or random
- Check expiration on resolve
