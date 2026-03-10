package strategy;

import util.HashUtil;

/**
 * Proof-of-Work mining strategy.
 * Finds a nonce such that the block hash starts with {@code difficulty} leading zeros.
 * Higher difficulty means more computation (longer to mine).
 */
public class ProofOfWorkStrategy implements MiningStrategy {

    private final int difficulty;
    private final String target;

    public ProofOfWorkStrategy(int difficulty) {
        this.difficulty = difficulty;
        this.target = "0".repeat(difficulty);
    }

    @Override
    public int mine(int index, String previousHash, String merkleRoot) {
        int nonce = 0;
        while (true) {
            String candidate = HashUtil.sha256(index + previousHash + merkleRoot + nonce);
            if (candidate.startsWith(target)) {
                return nonce;
            }
            nonce++;
        }
    }

    public int getDifficulty() {
        return difficulty;
    }
}
