package strategy;

import model.Transaction;

import java.util.List;

/**
 * Strategy interface for block mining.
 * Implementations decide how nonce is computed (proof-of-work difficulty).
 */
public interface MiningStrategy {
    /**
     * Mines a nonce for the given block data.
     *
     * @param index        block index
     * @param previousHash hash of the previous block
     * @param merkleRoot   merkle root of the transactions
     * @return nonce that satisfies the mining condition
     */
    int mine(int index, String previousHash, String merkleRoot);
}
