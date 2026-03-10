package model;

import exception.InvalidBlockException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The blockchain: an ordered, append-only list of blocks.
 * Each block references the hash of its predecessor, making the chain tamper-evident.
 */
public class Blockchain {

    private static final String GENESIS_PREVIOUS_HASH = "0000000000000000000000000000000000000000000000000000000000000000";

    private final List<Block> chain;

    public Blockchain(Block genesisBlock) {
        this.chain = new ArrayList<>();
        chain.add(genesisBlock);
    }

    /**
     * Appends a pre-validated block to the chain.
     * The block must reference the hash of the current last block.
     */
    public void addBlock(Block block) {
        Block lastBlock = getLastBlock();
        if (!block.getPreviousHash().equals(lastBlock.getHash())) {
            throw new InvalidBlockException(
                    String.format("Block %d has invalid previousHash. Expected '%s', got '%s'",
                            block.getIndex(), lastBlock.getHash(), block.getPreviousHash()));
        }
        chain.add(block);
    }

    public Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    public int size() {
        return chain.size();
    }

    public List<Block> getChain() {
        return Collections.unmodifiableList(chain);
    }

    public static String getGenesisPreviousHash() {
        return GENESIS_PREVIOUS_HASH;
    }
}
