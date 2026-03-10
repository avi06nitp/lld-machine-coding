# AI-Based Code Review System

An automated static code analysis system that reviews code snippets for naming conventions, complexity, security vulnerabilities, and best practices. Supports multiple programming languages through pluggable strategy implementations.

---

## Architecture

```
Presentation Layer (Controller)
    ↓
Application Layer (Service, Invoker)
    ↓
Domain Layer (Commands, Strategies, Models, Enums)
    ↓
Infrastructure Layer (Exceptions, Factory)
```

---

## Design Patterns

### 1. Command Pattern — Code Analysis Operations
Each analysis type is encapsulated as a `Command` object:
- `NamingConventionCommand` — checks variable/method/class names
- `CodeComplexityCommand`  — checks method length, nesting depth, parameter count
- `SecurityAnalysisCommand` — checks SQL injection, hardcoded secrets, unsafe APIs
- `BestPracticesCommand`   — checks empty catch blocks, magic numbers, raw types

The `CodeReviewInvoker` manages and executes the command queue. Adding a new analysis type requires only adding a new `AnalysisCommand` implementation — no existing code changes.

```java
invoker.addCommand(new NamingConventionCommand(strategy));
invoker.addCommand(new SecurityAnalysisCommand(strategy));
// easily extensible — add more here
```

### 2. Strategy Pattern — Language-Specific Rule Checking
Different languages have different conventions. Each language's rules are encapsulated in a `RuleCheckingStrategy`:

| Strategy | Language | Rules |
|---|---|---|
| `JavaRuleCheckingStrategy` | Java | PascalCase classes, UPPER_SNAKE_CASE constants, camelCase variables, SQL injection, empty catch, raw types |
| `PythonRuleCheckingStrategy` | Python | PEP 8 naming, bare except, eval(), pickle, mutable default args |
| `GeneralRuleCheckingStrategy` | JS/TS/Text | Line length, file size, TODO comments, hardcoded secrets |

### 3. Factory Pattern — Strategy Creation
`ReviewStrategyFactory` centralises strategy instantiation, decoupling callers from concrete implementations:

```java
RuleCheckingStrategy strategy = factory.createStrategy(Language.JAVA);
// → returns a JavaRuleCheckingStrategy
```

---

## SOLID Principles

| Principle | How it's applied |
|---|---|
| **SRP** | `CodeReviewService` orchestrates, `ReportService` formats output, `CodeReviewController` handles I/O |
| **OCP** | Add new languages by implementing `RuleCheckingStrategy`; add new checks by implementing `AnalysisCommand` |
| **LSP** | Any `RuleCheckingStrategy` / `AnalysisCommand` is interchangeable |
| **ISP** | Focused interfaces: `RuleCheckingStrategy` has 4 targeted methods; `AnalysisCommand` has 2 |
| **DIP** | `CodeReviewService` depends on `RuleCheckingStrategy` interface, not concrete strategy classes |

---

## Project Structure

```
ai-code-review/
├── AICodeReviewSystem.java          # Main entry point
├── AICodeReviewSystemTest.java      # Self-contained test suite (8 tests)
├── controller/
│   └── CodeReviewController.java   # User interaction and I/O
├── command/
│   ├── AnalysisCommand.java         # Command interface
│   ├── NamingConventionCommand.java
│   ├── CodeComplexityCommand.java
│   ├── SecurityAnalysisCommand.java
│   ├── BestPracticesCommand.java
│   └── CodeReviewInvoker.java       # Manages and executes commands
├── strategy/
│   ├── RuleCheckingStrategy.java    # Strategy interface
│   ├── JavaRuleCheckingStrategy.java
│   ├── PythonRuleCheckingStrategy.java
│   └── GeneralRuleCheckingStrategy.java
├── model/
│   ├── CodeSnippet.java             # Input: code being reviewed
│   ├── ReviewComment.java           # Individual finding
│   └── ReviewReport.java           # Output: aggregated findings
├── factory/
│   └── ReviewStrategyFactory.java
├── service/
│   ├── CodeReviewService.java       # Orchestrates the review
│   └── ReportService.java          # Formats and prints the report
├── enums/
│   ├── Language.java                # JAVA, PYTHON, JAVASCRIPT, TYPESCRIPT, GENERAL
│   ├── Severity.java                # ERROR, WARNING, INFO
│   └── ReviewCategory.java          # NAMING_CONVENTION, CODE_COMPLEXITY, SECURITY, BEST_PRACTICES
└── exception/
    ├── CodeReviewException.java
    └── UnsupportedLanguageException.java
```

---

## Running the Application

```bash
# Compile
javac -d out $(find . -name "*.java")

# Run interactive review session
java -cp out AICodeReviewSystem

# Run tests
java -cp out AICodeReviewSystemTest
```

---

## Sample Output

```
╔══════════════════════════════════════════════════════════════════════╗
║           AI-BASED CODE REVIEW SYSTEM                               ║
╚══════════════════════════════════════════════════════════════════════╝

Enter file name (e.g., MyClass.java): BadCode.java
Detected language: JAVA
...

══════════════════════════════════════════════════════════════════════
  AI CODE REVIEW REPORT
══════════════════════════════════════════════════════════════════════
  File     : BadCode.java
  Duration : 11 ms
  Issues   : 2 errors, 7 warnings, 3 info

  NAMING CONVENTION
────────────────────────────────────────────────────────────────────
  [ERROR]   [Naming Convention] Line 1: Class name 'myClass' should start with uppercase → Rename to 'MyClass'

  SECURITY
────────────────────────────────────────────────────────────────────
  [ERROR]   [Security] Line 2: Potential hardcoded credential → Use environment variables

  SUMMARY
────────────────────────────────────────────────────────────────────
  Errors:      2
  Warnings:    7
  Info:        3
  Total:       12

  VERDICT: ✗ REVIEW FAILED — Critical issues must be resolved before merging.
```

---

## Extensibility

**Add a new language:**
1. Add an entry to the `Language` enum
2. Implement `RuleCheckingStrategy` with language-specific rules
3. Register it in `ReviewStrategyFactory.createStrategy()`

**Add a new analysis category:**
1. Add an entry to the `ReviewCategory` enum (if needed)
2. Implement `AnalysisCommand`
3. Add it to `CodeReviewService.buildInvoker()`

No other changes required — this is the Open/Closed Principle in action.
