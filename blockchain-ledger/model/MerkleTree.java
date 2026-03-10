package model;

import util.HashUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Merkle Tree implementation for verifying transaction integrity.
 * Builds a binary hash tree where each leaf is a transaction hash,
 * and each internal node is the hash of its two children.
 * The root hash summarizes all transactions in O(log n) space.
 */
public class MerkleTree {

    private final String root;

    public MerkleTree(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            this.root = HashUtil.sha256("empty-block");
        } else {
            this.root = buildRoot(transactions);
        }
    }

    private String buildRoot(List<Transaction> transactions) {
        List<String> hashes = new ArrayList<>();
        for (Transaction tx : transactions) {
            hashes.add(tx.getHash());
        }
        return reduce(hashes);
    }

    private String reduce(List<String> hashes) {
        if (hashes.size() == 1) {
            return hashes.get(0);
        }

        // Duplicate last element if odd count so each node has a pair
        if (hashes.size() % 2 != 0) {
            hashes.add(hashes.get(hashes.size() - 1));
        }

        List<String> parentLevel = new ArrayList<>();
        for (int i = 0; i < hashes.size(); i += 2) {
            parentLevel.add(HashUtil.sha256(hashes.get(i) + hashes.get(i + 1)));
        }
        return reduce(parentLevel);
    }

    public String getRoot() {
        return root;
    }
}
