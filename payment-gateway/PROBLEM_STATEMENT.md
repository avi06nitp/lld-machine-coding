# Payment Gateway System

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a payment gateway that processes payments through multiple providers.

---

## Requirements

### Must Have (Core)
- Register payment providers (PayPal, Stripe, etc. - simulated)
- Add payment methods for users (card, bank account)
- Process payment (select provider, attempt transaction)
- Retry with fallback provider on failure
- Transaction history
- Refund support

### Good to Have
- Provider health check
- Routing rules (prefer provider based on amount/currency)

### Not Expected
- Real payment provider integration
- PCI compliance handling
- Fraud detection
- Subscription/recurring payments

---

## Expected Input/Output

```
Input:
> register_provider STRIPE 0.029 0.30    # 2.9% + $0.30 fee
> register_provider PAYPAL 0.034 0.49
> add_payment_method U1 CARD "4111-1111-1111-1111" "12/25"
> process_payment U1 100.00 USD "Order #123"
> simulate_failure STRIPE
> process_payment U1 50.00 USD "Order #124"
> refund TXN-001 50.00
> transaction_history U1

Output:
Provider STRIPE registered (Fee: 2.9% + $0.30)
Provider PAYPAL registered (Fee: 3.4% + $0.49)

Payment method added for U1:
  Type: CARD
  Number: ****1111
  Expiry: 12/25

Processing payment: $100.00 USD
  Attempting STRIPE...
  SUCCESS!
  Transaction ID: TXN-001
  Amount: $100.00
  Fee: $3.20 (2.9% + $0.30)
  Net: $96.80

STRIPE marked as unavailable (simulated failure)

Processing payment: $50.00 USD
  Attempting STRIPE... FAILED (provider unavailable)
  Attempting PAYPAL (fallback)...
  SUCCESS!
  Transaction ID: TXN-002
  Amount: $50.00
  Fee: $2.19 (3.4% + $0.49)
  Net: $47.81

Refund initiated for TXN-001:
  Amount: $50.00
  Refund ID: REF-001
  Status: PROCESSED
  Note: Partial refund (original: $100.00)

Transaction History for U1:
1. TXN-001 | $100.00 | STRIPE | PARTIAL_REFUND | Order #123
2. TXN-002 | $50.00 | PAYPAL | SUCCESS | Order #124
3. REF-001 | -$50.00 | STRIPE | REFUND | Refund for TXN-001
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles payment flow |
| **OO Design** | 25% | Proper abstraction for providers |
| **Design Patterns** | 20% | Strategy, Chain of Responsibility |
| **Code Quality** | 15% | Clean code, error handling |
| **Extensibility** | 10% | Easy to add providers |
| **Edge Cases** | 5% | Insufficient balance, all providers down |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement idempotency for payments?
2. How would you handle currency conversion?
3. How would you implement webhook handling?
4. How would you add support for recurring payments?

### Reliability
5. How would you handle network timeouts?
6. How would you prevent duplicate charges?
7. How would you implement retry with exponential backoff?

### Scalability
8. How would you handle millions of transactions?
9. How would you implement fraud detection?
10. How would you add support for multiple currencies?

---

## Transaction States

```
Payment:
PENDING → PROCESSING → SUCCESS
                    → FAILED

Refund:
REFUND_PENDING → REFUND_PROCESSING → REFUNDED
                                   → REFUND_FAILED

Final States:
- SUCCESS
- FAILED
- PARTIAL_REFUND
- FULL_REFUND
```

---

## Provider Interface

```
interface PaymentProvider {
    String getName();
    boolean isAvailable();
    PaymentResult charge(PaymentRequest request);
    RefundResult refund(String transactionId, double amount);
    double calculateFee(double amount);
}
```

---

## Hints for Candidate

- Use Strategy pattern for different providers
- Chain of Responsibility for fallback logic
- Transaction should be immutable after creation
- Consider a PaymentProcessor that orchestrates the flow
