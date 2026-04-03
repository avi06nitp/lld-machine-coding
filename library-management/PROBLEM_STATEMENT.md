# Library Management System

**Difficulty:** ⭐⭐⭐☆☆ (Medium)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a library management system that handles book lending, returns, and member management.

---

## Requirements

### Must Have (Core)
- Add/remove books (with multiple copies)
- Register members
- Search books (by title, author, ISBN)
- Borrow book (check availability, max 5 books per member)
- Return book
- Calculate fine ($1/day for overdue, 14-day lending period)
- View borrowed books for a member

### Good to Have
- Reserve book (if all copies borrowed)
- Different membership types with different limits

### Not Expected
- Online catalog integration
- Payment processing
- Digital books (e-books)
- Notifications

---

## Expected Input/Output

```
Input:
> add_book "Clean Code" "Robert Martin" "978-0132350884" 3
> add_book "Design Patterns" "Gang of Four" "978-0201633610" 2
> register_member M1 "Alice" STANDARD
> search_book "Clean"
> borrow M1 "978-0132350884"
> borrow M1 "978-0132350884"
> list_borrowed M1
> return M1 "978-0132350884" 2024-01-20    # Assuming borrowed on 2024-01-01

Output:
Book added: "Clean Code" (3 copies) - ISBN: 978-0132350884
Book added: "Design Patterns" (2 copies) - ISBN: 978-0201633610

Member registered: M1 (Alice) - STANDARD membership

Search results for "Clean":
1. Clean Code by Robert Martin [Available: 3/3]

Book borrowed successfully:
  Member: Alice (M1)
  Book: Clean Code
  Due date: 2024-01-15
  Available copies: 2/3

Error: Member already has this book borrowed

Borrowed books for Alice (M1):
1. Clean Code (Due: 2024-01-15)

Book returned:
  Member: Alice (M1)
  Book: Clean Code
  Return date: 2024-01-20
  Days overdue: 5
  Fine: $5.00
  Available copies: 3/3
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles library flow |
| **OO Design** | 25% | Proper modeling of Book, Member, Loan |
| **Design Patterns** | 20% | Factory, Strategy for membership |
| **Code Quality** | 15% | Clean code, encapsulation |
| **Extensibility** | 10% | Easy to add item types, membership |
| **Edge Cases** | 5% | Max limit, no copies available |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement the reservation queue?
2. How would you send reminders for due books?
3. How would you handle lost books?
4. How would you track book condition (new, good, worn)?

### Extensibility
5. How would you add support for magazines, DVDs?
6. How would you implement a recommendation system?
7. How would you add inter-library loan support?

### SOLID Principles
8. How does your design follow Dependency Inversion?
9. How would you add new membership types without changing existing code?
10. How would you test fine calculation logic?

---

## Hints for Candidate

- Separate Book (metadata) from BookCopy (physical item)
- Loan/Transaction tracks the borrowing relationship
- Consider interface for different LibraryItem types
- Fine calculation should be a separate service/strategy
