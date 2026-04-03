# AI-Based Code Review System

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a code review system that performs static analysis and suggests improvements.

---

## Requirements

### Must Have (Core)
- Parse code (support one language: Java/Python/JavaScript)
- Detect code smells:
  - Long methods (> N lines)
  - Long parameter list (> 3 params)
  - Magic numbers (hardcoded numeric values)
  - Empty catch blocks
- Report findings with line numbers
- Severity levels (ERROR, WARNING, INFO)

### Good to Have
- Detect duplicate code blocks
- Complexity analysis (cyclomatic complexity)

### Not Expected
- Full AST parsing (simple regex/string matching is fine)
- Auto-fix suggestions
- Integration with Git
- Multiple file analysis

---

## Expected Input/Output

```
Input:
> analyze --file example.java

// example.java content:
public class Calculator {
    public int calculate(int a, int b, int c, int d, int e) {
        int result = 0;
        if (a > 100) {
            result = a * 3.14 * 2;
        }
        try {
            result = b / c;
        } catch (Exception e) {
        }
        // ... 60 more lines of code
        return result;
    }
}

Output:
Code Review Report for: example.java
=====================================

ERRORS (2):
  Line 9: Empty catch block - exceptions should be handled or logged
  Line 2: Long parameter list (5 params) - consider using a parameter object

WARNINGS (2):
  Line 4: Magic number 100 - use a named constant
  Line 5: Magic number 3.14 - use Math.PI or a named constant

INFO (1):
  Line 2-65: Long method (63 lines) - consider breaking into smaller methods

Summary:
  Errors: 2
  Warnings: 2
  Info: 1
  Code Quality Score: 65/100
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, detects issues |
| **OO Design** | 25% | Proper abstraction for rules |
| **Design Patterns** | 20% | Strategy/Visitor for rules |
| **Code Quality** | 15% | Clean code, extensible rules |
| **Extensibility** | 10% | Easy to add new rules |
| **Edge Cases** | 5% | Empty files, syntax edge cases |

---

## Follow-up Questions

### Design & Architecture
1. How would you add support for a new language?
2. How would you implement duplicate code detection?
3. How would you calculate cyclomatic complexity?
4. How would you add custom rules via configuration?

### Extensibility
5. How would you implement auto-fix suggestions?
6. How would you add severity configuration per rule?
7. How would you implement rule categories?

### Integration
8. How would you integrate with CI/CD pipelines?
9. How would you handle incremental analysis (only changed files)?
10. How would you add support for comments/annotations to suppress warnings?

---

## Code Smells to Detect

| Smell | Detection Method | Severity |
|-------|-----------------|----------|
| Long method | Count lines > threshold | INFO |
| Long params | Count parameters > 3 | ERROR |
| Magic numbers | Find hardcoded numeric literals | WARNING |
| Empty catch | Catch block with no statements | ERROR |
| Deep nesting | Count nested if/for/while > 3 | WARNING |
| Long line | Line length > 120 chars | INFO |

---

## Rule Interface

```
interface CodeRule {
    String getName();
    Severity getSeverity();
    List<Issue> analyze(String code);
}

class Issue {
    int lineNumber;
    String message;
    Severity severity;
    String ruleName;
}
```

---

## Hints for Candidate

- Use Strategy pattern for different rules
- Rules can use regex or simple string parsing
- Consider a RuleEngine that runs all rules
- Report should aggregate issues by severity
