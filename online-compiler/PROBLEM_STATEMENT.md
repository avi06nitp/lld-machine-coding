# Online Code Compiler

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement an online code compiler that executes code in multiple languages safely.

---

## Requirements

### Must Have (Core)
- Support multiple languages (Java, Python, C++)
- Submit code for execution
- Capture output (stdout, stderr)
- Execution timeout (kill long-running code)
- Input support (stdin)
- Return execution result (output, errors, time taken)

### Good to Have
- Memory limit enforcement
- Execution queue management

### Not Expected
- Actual sandboxing (simulate execution)
- Code persistence
- Collaborative editing
- Syntax highlighting

---

## Expected Input/Output

```
Input:
> submit PYTHON "print('Hello World')"
> submit JAVA "
  public class Main {
    public static void main(String[] args) {
      System.out.println(\"Hello Java\");
    }
  }"
> submit PYTHON "
  n = int(input())
  print(n * 2)
  " --input "5"
> submit PYTHON "while True: pass" --timeout 2000

Output:
Submission ID: SUB-001
Language: PYTHON
Status: QUEUED

Executing SUB-001...

Result:
  Status: SUCCESS
  Output: Hello World
  Time: 45ms
  Memory: 8.2 MB

---

Submission ID: SUB-002
Language: JAVA
Status: QUEUED

Compiling SUB-002...
Compilation: SUCCESS

Executing SUB-002...

Result:
  Status: SUCCESS
  Output: Hello Java
  Time: 120ms
  Memory: 32.5 MB

---

Submission ID: SUB-003
Language: PYTHON
Status: QUEUED
Input provided: "5"

Executing SUB-003...

Result:
  Status: SUCCESS
  Output: 10
  Time: 38ms
  Memory: 8.1 MB

---

Submission ID: SUB-004
Language: PYTHON
Status: QUEUED
Timeout: 2000ms

Executing SUB-004...

Result:
  Status: TIME_LIMIT_EXCEEDED
  Output: (none)
  Error: Execution killed after 2000ms
  Time: 2000ms (limit exceeded)
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles execution |
| **OO Design** | 25% | Proper abstraction for languages |
| **Design Patterns** | 20% | Strategy, Factory, Template |
| **Code Quality** | 15% | Clean code, error handling |
| **Extensibility** | 10% | Easy to add new languages |
| **Edge Cases** | 5% | Timeout, compilation errors |

---

## Follow-up Questions

### Design & Architecture
1. How would you add support for a new language?
2. How would you implement the execution queue?
3. How would you isolate code execution for security?
4. How would you handle compilation vs interpretation?

### Security
5. How would you prevent malicious code (file access, network)?
6. How would you implement resource limits?
7. How would you handle fork bombs?

### Scalability
8. How would you handle 10000 concurrent submissions?
9. How would you implement distributed execution?
10. How would you add support for multi-file projects?

---

## Execution Result States

```
QUEUED → COMPILING → COMPILED → EXECUTING → SUCCESS
              ↓                      ↓
      COMPILATION_ERROR         RUNTIME_ERROR
                                TIME_LIMIT_EXCEEDED
                                MEMORY_LIMIT_EXCEEDED
```

---

## Language Executor Interface

```
interface LanguageExecutor {
    String getLanguage();
    boolean needsCompilation();
    CompilationResult compile(String code);
    ExecutionResult execute(String code, String input, int timeout);
}

class ExecutionResult {
    Status status;
    String output;
    String error;
    long executionTime;
    long memoryUsed;
}
```

---

## Hints for Candidate

- Use Strategy pattern for different language executors
- Template method for compile-then-execute flow
- Consider a Submission class to track state
- Simulate execution with Thread.sleep and random results for demo
