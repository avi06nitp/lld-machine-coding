# Real-time Chat Application (WhatsApp Clone)

**Difficulty:** ⭐⭐⭐⭐☆ (Medium-Hard)
**Time:** 90-120 mins
**Role:** Senior SDE (3+ YoE)

---

## Problem Statement

Design and implement a chat application supporting direct messages and group chats.

---

## Requirements

### Must Have (Core)
- Register users
- Send direct message (1-to-1)
- Create group and add members
- Send group message (1-to-many)
- View conversation history
- Mark messages as read

### Good to Have
- User online/offline status
- Typing indicator (simulated)

### Not Expected
- Real-time push (use polling/request-response)
- Media sharing
- End-to-end encryption
- Voice/video calls

---

## Expected Input/Output

```
Input:
> register_user U1 "Alice"
> register_user U2 "Bob"
> register_user U3 "Charlie"
> send_message U1 U2 "Hi Bob!"
> send_message U2 U1 "Hello Alice!"
> create_group G1 "Friends" U1
> add_to_group G1 U2
> add_to_group G1 U3
> send_group_message U1 G1 "Hello everyone!"
> get_messages U1 U2
> get_group_messages G1
> mark_read U2 MSG-001

Output:
User U1 "Alice" registered
User U2 "Bob" registered
User U3 "Charlie" registered

Message sent:
  From: Alice → To: Bob
  Message: "Hi Bob!"
  ID: MSG-001
  Time: 10:30:00

Message sent:
  From: Bob → To: Alice
  Message: "Hello Alice!"
  ID: MSG-002
  Time: 10:30:15

Group "Friends" created by Alice
Group ID: G1

Bob added to "Friends"
Charlie added to "Friends"

Group message sent:
  From: Alice → Group: Friends
  Message: "Hello everyone!"
  ID: MSG-003
  Delivered to: Bob, Charlie

Conversation between Alice and Bob:
  [10:30:00] Alice: Hi Bob! ✓✓
  [10:30:15] Bob: Hello Alice! ✓

Messages in "Friends":
  [10:31:00] Alice: Hello everyone!
    Read by: (none)

MSG-001 marked as read by Bob
```

---

## Evaluation Criteria

| Criteria | Weight | What to Look For |
|----------|--------|------------------|
| **Working Code** | 25% | Compiles, runs, handles messaging |
| **OO Design** | 25% | Proper modeling of User, Chat, Message |
| **Design Patterns** | 20% | Observer, Mediator |
| **Code Quality** | 15% | Clean code, separation of concerns |
| **Extensibility** | 10% | Easy to add features |
| **Edge Cases** | 5% | User not in group, empty chat |

---

## Follow-up Questions

### Design & Architecture
1. How would you implement message delivery status (sent, delivered, read)?
2. How would you handle message ordering?
3. How would you implement "last seen" feature?
4. How would you add reply-to-message feature?

### Scalability
5. How would you handle millions of messages per second?
6. How would you store chat history efficiently?
7. How would you implement message search?

### Real-time
8. How would you implement real-time notifications?
9. How would you handle offline message delivery?
10. How would you implement typing indicators?

---

## Message Status Flow

```
Message States:
1. SENT - Message sent to server
2. DELIVERED - Message received by recipient's device
3. READ - Message opened/viewed by recipient

Direct Message:
  SENT → DELIVERED → READ

Group Message:
  SENT → DELIVERED_TO_SOME → DELIVERED_TO_ALL → READ_BY_SOME → READ_BY_ALL
```

---

## Key Models

```
User:
  - id, name, status (online/offline)
  - lastSeen

Message:
  - id, sender, content, timestamp
  - status (SENT, DELIVERED, READ)

DirectChat:
  - user1, user2
  - messages[]

Group:
  - id, name, admin
  - members[]
  - messages[]
```

---

## Hints for Candidate

- Separate DirectChat and GroupChat
- Message should track delivery status per recipient (for groups)
- Consider a ChatService to manage all operations
- Observer pattern for message notifications
