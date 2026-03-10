# URL Shortener (Bit.ly Clone)

A low-level design implementation of a URL shortening service, demonstrating clean architecture, SOLID principles, and classic design patterns.

## Features

- Shorten any HTTP/HTTPS URL to a 6-character code
- Custom alias support (e.g. `https://short.ly/goog`)
- Optional URL expiry with millisecond precision
- Click-count analytics
- LRU cache for fast lookups
- Two short-code generation strategies (Base62 random, MD5 hash)

## Design Patterns

| Pattern   | Where Used                                                          |
|-----------|---------------------------------------------------------------------|
| Strategy  | `CodeGenerationStrategy` — swap between Base62 and Hash algorithms  |
| Factory   | `CodeGeneratorFactory` — creates the correct strategy by type       |
| Cache     | `UrlCache` — LRU eviction via `LinkedHashMap` access-order          |

## Architecture

```
url-shortener/
├── enums/
│   └── EncodingType.java              # BASE62 | HASH
├── exception/
│   ├── UrlNotFoundException.java
│   ├── UrlExpiredException.java
│   └── CustomAliasAlreadyExistsException.java
├── model/
│   ├── Url.java                       # Core domain entity
│   └── UrlStats.java                  # Read-only analytics snapshot
├── strategy/
│   ├── CodeGenerationStrategy.java    # Strategy interface
│   ├── Base62Strategy.java            # Random alphanumeric code
│   └── HashStrategy.java              # Deterministic MD5-based code
├── factory/
│   └── CodeGeneratorFactory.java      # Creates strategy by EncodingType
├── cache/
│   └── UrlCache.java                  # LRU cache (LinkedHashMap)
├── service/
│   └── UrlShortenerService.java       # Core business logic
├── controller/
│   └── UrlController.java             # CLI interaction layer
├── test/
│   └── UrlShortenerServiceTest.java   # Manual unit tests
└── UrlShortenerApp.java               # Entry point
```

## How to Run

```bash
cd url-shortener

# Compile
javac -cp . enums/*.java exception/*.java model/*.java strategy/*.java \
      factory/*.java cache/*.java service/*.java controller/*.java \
      test/*.java UrlShortenerApp.java

# Run tests
java -cp . test.UrlShortenerServiceTest

# Run application
java -cp . UrlShortenerApp
```

## SOLID Principles

- **S** — Each class has a single responsibility (`Url` is just data; `UrlShortenerService` handles business logic; `UrlCache` handles caching).
- **O** — New encoding strategies can be added without modifying existing code — just implement `CodeGenerationStrategy`.
- **L** — `Base62Strategy` and `HashStrategy` are interchangeable wherever `CodeGenerationStrategy` is expected.
- **I** — `CodeGenerationStrategy` exposes only the `generateCode` method; no bloated interfaces.
- **D** — `UrlShortenerService` depends on the `CodeGenerationStrategy` abstraction, not a concrete implementation.
