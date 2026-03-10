package model;

import exception.InvalidTransactionException;
import util.HashUtil;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Immutable representation of a blockchain transaction.
 * Once created, the transaction data cannot be altered.
 */
public final class Transaction {

    private final String transactionId;
    private final String sender;
    private final String receiver;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String hash;

    public Transaction(String sender, String receiver, double amount) {
        validate(sender, receiver, amount);
        this.transactionId = UUID.randomUUID().toString();
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.hash = computeHash();
    }

    private void validate(String sender, String receiver, double amount) {
        if (sender == null || sender.isBlank()) {
            throw new InvalidTransactionException("Sender cannot be null or empty");
        }
        if (receiver == null || receiver.isBlank()) {
            throw new InvalidTransactionException("Receiver cannot be null or empty");
        }
        if (amount <= 0) {
            throw new InvalidTransactionException("Transaction amount must be positive, got: " + amount);
        }
        if (sender.equals(receiver)) {
            throw new InvalidTransactionException("Sender and receiver cannot be the same");
        }
    }

    private String computeHash() {
        String data = transactionId + sender + receiver + amount + timestamp;
        return HashUtil.sha256(data);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return String.format("Transaction{id='%s', sender='%s', receiver='%s', amount=%.2f, timestamp=%s}",
                transactionId.substring(0, 8), sender, receiver, amount, timestamp);
    }
}
