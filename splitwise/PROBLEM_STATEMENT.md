# Splitwise Clone

**Difficulty:** ⭐⭐⭐☆☆ (Medium)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement an expense sharing application that tracks expenses among friends and calculates optimal settlements.

---

## Requirements

### Must Have (Core)
- Add users
- Create expense with multiple split types:
  - **EQUAL:** Split equally among participants
  - **EXACT:** Specify exact amounts for each participant
  - **PERCENT:** Specify percentages for each participant
- Show balances for a user (who owes whom how much)
- Show all outstanding balances

### Good to Have
- Groups (track expenses within a group)
- Simplify debts (minimize transactions)

### Not Expected
- Payment integration
- Currency conversion
- Expense categories and reports
- Recurring expenses

---

## Expected Input/Output

```
Input:
> add_user U1 "Alice" alice@email.com
> add_user U2 "Bob" bob@email.com
> add_user U3 "Charlie" charlie@email.com
> add_user U4 "Diana" diana@email.com
> expense U1 1000 4 U1 U2 U3 U4 EQUAL
> expense U2 500 2 U2 U3 EXACT 300 200
> expense U3 600 3 U1 U2 U3 PERCENT 40 30 30
> show_balance U1
> show_all_balances

Output:
User U1 (Alice) added
User U2 (Bob) added
User U3 (Charlie) added
User U4 (Diana) added

Expense added:
Alice paid $1000
Split: Alice($250), Bob($250), Charlie($250), Diana($250)

Expense added:
Bob paid $500
Split: Bob($300), Charlie($200)

Expense added:
Charlie paid $600
Split: Alice($240), Bob($180), Charlie($180)

Balances for Alice:
Bob owes Alice: $70.00
Charlie owes Alice: $10.00
Diana owes Alice: $250.00

All Balances:
Bob owes Alice: $70.00
Charlie owes Alice: $10.00
Diana owes Alice: $250.00
Charlie owes Bob: $20.00
```

### Calculation Breakdown

```
After expense 1 (Alice pays 1000, split equally):
  Bob owes Alice: 250
  Charlie owes Alice: 250
  Diana owes Alice: 250

After expense 2 (Bob pays 500, exact split):
  Charlie owes Bob: 200

After expense 3 (Charlie pays 600, percent split):
  Alice owes Charlie: 240
  Bob owes Charlie: 180

Final (after netting):
  Bob owes Alice: 250 - 180 = 70
  Charlie owes Alice: 250 - 240 = 10
  Diana owes Alice: 250
  Charlie owes Bob: 200 - 180 = 20
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, correct calculations |
| **OO Design** | 25% | Proper modeling of User, Expense, Split |
| **Design Patterns** | 20% | Strategy for split types |
| **Code Quality** | 15% | Clean code, accurate math |
| **Extensibility** | 10% | Easy to add new split types |
| **Edge Cases** | 5% | Rounding errors, validation |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement the simplify debts feature?
2. How would you add group functionality?
3. How would you handle expense edits/deletions?
4. How would you implement recurring expenses?

### Edge Cases
5. How do you handle rounding errors in percentage splits?
6. What if percentage doesn't add up to 100%?
7. What if exact amounts don't add up to total?

### Scalability
8. How would you design this for millions of users?
9. How would you efficiently compute balances for users with 1000s of expenses?
10. How would you add activity feed (who added what expense)?

---

## Hints for Candidate

- Use a Map<UserId, Map<UserId, Double>> for balances
- Strategy pattern works well for different split types
- Consider validating splits before accepting expense
- Net the balances (A owes B $50, B owes A $30 = A owes B $20)
