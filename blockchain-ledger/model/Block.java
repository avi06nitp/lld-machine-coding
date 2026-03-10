package model;

import util.HashUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Immutable block in the blockchain.
 * Contains a list of transactions, links to the previous block via previousHash,
 * and a Merkle root that summarizes all transactions for tamper detection.
 */
public final class Block {

    private final int index;
    private final String previousHash;
    private final List<Transaction> transactions;
    private final String merkleRoot;
    private final LocalDateTime timestamp;
    private final int nonce;
    private final String hash;

    public Block(int index, String previousHash, List<Transaction> transactions, int nonce) {
        this.index = index;
        this.previousHash = previousHash;
        this.transactions = Collections.unmodifiableList(List.copyOf(transactions));
        this.merkleRoot = new MerkleTree(transactions).getRoot();
        this.timestamp = LocalDateTime.now();
        this.nonce = nonce;
        this.hash = computeHash();
    }

    /**
     * Hash formula: index + previousHash + merkleRoot + nonce.
     * Timestamp is informational metadata and is intentionally excluded
     * so that the mining strategy can compute the same hash without knowing the future timestamp.
     */
    private String computeHash() {
        String data = index + previousHash + merkleRoot + nonce;
        return HashUtil.sha256(data);
    }

    public int getIndex() {
        return index;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return String.format(
                "Block{index=%d, hash='%s...', prevHash='%s...', transactions=%d, nonce=%d}",
                index,
                hash.substring(0, 12),
                previousHash.length() >= 12 ? previousHash.substring(0, 12) : previousHash,
                transactions.size(),
                nonce
        );
    }
}
