package strategy;

/**
 * Simple mining strategy with no proof-of-work requirement.
 * Nonce is always 0. Useful for testing and demos where speed matters.
 */
public class SimpleMiningStrategy implements MiningStrategy {

    @Override
    public int mine(int index, String previousHash, String merkleRoot) {
        return 0;
    }
}
