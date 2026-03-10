package service;

import model.Block;
import model.Blockchain;
import model.MerkleTree;
import util.HashUtil;

import java.util.List;

/**
 * Validates blockchain and block integrity.
 * Checks hash linkage and Merkle root consistency throughout the chain.
 */
public class ValidationService {

    /**
     * Returns true if the entire chain is valid:
     * - Each block's hash matches its recomputed hash
     * - Each block's previousHash matches the prior block's hash
     * - Each block's merkleRoot matches its transactions
     */
    public boolean isChainValid(Blockchain blockchain) {
        List<Block> chain = blockchain.getChain();

        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            if (!isBlockHashValid(current)) {
                System.out.println("Invalid hash at block " + i);
                return false;
            }

            if (!current.getPreviousHash().equals(previous.getHash())) {
                System.out.println("Broken chain link at block " + i);
                return false;
            }

            if (!isMerkleRootValid(current)) {
                System.out.println("Invalid merkle root at block " + i);
                return false;
            }
        }
        return true;
    }

    /** Verifies a single block's hash and merkle root. */
    public boolean isBlockValid(Block block) {
        return isBlockHashValid(block) && isMerkleRootValid(block);
    }

    private boolean isBlockHashValid(Block block) {
        // Must match Block.computeHash() — timestamp excluded intentionally (see Block javadoc)
        String recomputed = HashUtil.sha256(
                block.getIndex() + block.getPreviousHash() + block.getMerkleRoot() + block.getNonce());
        return recomputed.equals(block.getHash());
    }

    private boolean isMerkleRootValid(Block block) {
        String recomputed = new MerkleTree(block.getTransactions()).getRoot();
        return recomputed.equals(block.getMerkleRoot());
    }
}
