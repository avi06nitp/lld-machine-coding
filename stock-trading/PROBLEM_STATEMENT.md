# Stock Trading Platform

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a simplified stock trading platform with order matching and portfolio management.

---

## Requirements

### Must Have (Core)
- Register users with initial balance
- Place orders (BUY/SELL, stock symbol, quantity, price)
- Order matching (price-time priority)
- View order book for a stock
- User portfolio (holdings and balance)
- Order status (PENDING, PARTIAL, FILLED, CANCELLED)

### Good to Have
- Market orders (execute at best available price)
- Cancel pending orders

### Not Expected
- Real market data integration
- Options/Futures
- Margin trading
- Short selling

---

## Expected Input/Output

```
Input:
> register_user U1 "Alice" 10000
> register_user U2 "Bob" 10000
> place_order U1 BUY AAPL 10 150.00
> place_order U2 SELL AAPL 5 150.00
> place_order U2 SELL AAPL 3 150.00
> order_book AAPL
> portfolio U1
> portfolio U2

Output:
User U1 (Alice) registered with balance $10000.00

User U2 (Bob) registered with balance $10000.00

Order placed: U1 BUY 10 AAPL @ $150.00
Order ID: ORD-001 (Status: PENDING)

Order placed: U2 SELL 5 AAPL @ $150.00
Order ID: ORD-002
Trade executed: 5 AAPL @ $150.00
  Buyer: U1, Seller: U2
  Total: $750.00
ORD-001 Status: PARTIAL (5/10 filled)
ORD-002 Status: FILLED

Order placed: U2 SELL 3 AAPL @ $150.00
Order ID: ORD-003
Trade executed: 3 AAPL @ $150.00
  Buyer: U1, Seller: U2
  Total: $450.00
ORD-001 Status: PARTIAL (8/10 filled)
ORD-003 Status: FILLED

Order Book for AAPL:
BUY:
  $150.00 x 2 (ORD-001)
SELL:
  (empty)

Portfolio for Alice (U1):
  Balance: $8800.00
  Holdings:
    AAPL: 8 shares (Avg: $150.00)
  Pending Orders: 1

Portfolio for Bob (U2):
  Balance: $11200.00
  Holdings:
    (empty - sold all)
  Pending Orders: 0
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, correct matching |
| **OO Design** | 25% | Proper modeling of Order, Trade, Portfolio |
| **Design Patterns** | 20% | Observer, Command, Strategy |
| **Code Quality** | 15% | Clean code, correct calculations |
| **Extensibility** | 10% | Easy to add order types |
| **Edge Cases** | 5% | Insufficient balance, partial fills |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement market orders?
2. How would you handle order modification?
3. How would you implement stop-loss orders?
4. How would you add support for multiple exchanges?

### Concurrency
5. How would you handle concurrent orders for the same stock?
6. How would you ensure fairness in order matching?
7. How would you implement order locking?

### Scalability
8. How would you handle millions of orders per second?
9. How would you implement a matching engine?
10. How would you add real-time price feeds?

---

## Order Matching Logic

### Price-Time Priority
1. **Price Priority:** Better price gets matched first
   - For BUY: Higher price first
   - For SELL: Lower price first
2. **Time Priority:** Same price → Earlier order first

### Matching Algorithm
```
When new SELL order arrives:
1. Find best BUY orders (highest price first)
2. If SELL price <= BUY price, match
3. Execute at BUY price (price improvement for seller)
4. Continue until SELL quantity exhausted or no matching BUYs
```

---

## Hints for Candidate

- Order Book: Two sorted structures (BUY desc by price, SELL asc by price)
- Use TreeMap<Price, Queue<Order>> for efficient matching
- Trade is the result of matching, links buyer and seller
- Portfolio tracks holdings and calculates average cost
