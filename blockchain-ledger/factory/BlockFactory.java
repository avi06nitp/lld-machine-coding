package factory;

import model.Block;
import model.MerkleTree;
import model.Transaction;
import strategy.MiningStrategy;

import java.util.List;

/**
 * Factory responsible for creating mined blocks.
 * Delegates the nonce computation to the injected {@link MiningStrategy},
 * keeping block creation and mining logic decoupled.
 */
public class BlockFactory {

    private final MiningStrategy miningStrategy;

    public BlockFactory(MiningStrategy miningStrategy) {
        this.miningStrategy = miningStrategy;
    }

    /**
     * Creates a new block by mining the nonce then constructing the immutable Block.
     */
    public Block createBlock(int index, String previousHash, List<Transaction> transactions) {
        String merkleRoot = new MerkleTree(transactions).getRoot();
        int nonce = miningStrategy.mine(index, previousHash, merkleRoot);
        return new Block(index, previousHash, transactions, nonce);
    }
}
