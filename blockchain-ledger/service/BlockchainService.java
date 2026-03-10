package service;

import exception.InvalidTransactionException;
import factory.BlockFactory;
import model.Block;
import model.Blockchain;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Orchestrates the blockchain ledger:
 * - Accepts pending transactions
 * - Mines and commits them in batches (one block per commit)
 * - Exposes balance and history queries
 */
public class BlockchainService {

    private final Blockchain blockchain;
    private final BlockFactory blockFactory;
    private final ValidationService validationService;
    private final List<Transaction> pendingTransactions;
    private final int transactionsPerBlock;

    public BlockchainService(Blockchain blockchain, BlockFactory blockFactory,
                             ValidationService validationService, int transactionsPerBlock) {
        this.blockchain = blockchain;
        this.blockFactory = blockFactory;
        this.validationService = validationService;
        this.pendingTransactions = new ArrayList<>();
        this.transactionsPerBlock = transactionsPerBlock;
    }

    /**
     * Adds a transaction to the pending pool.
     * Automatically mines a block when the pool reaches {@code transactionsPerBlock}.
     */
    public Transaction addTransaction(String sender, String receiver, double amount) {
        Transaction tx = new Transaction(sender, receiver, amount);
        pendingTransactions.add(tx);
        System.out.printf("Transaction added: %s%n", tx);

        if (pendingTransactions.size() >= transactionsPerBlock) {
            minePendingTransactions();
        }
        return tx;
    }

    /**
     * Mines all pending transactions into a new block regardless of pool size.
     * Useful for forcing a commit at the end of a session.
     */
    public Block minePendingTransactions() {
        if (pendingTransactions.isEmpty()) {
            System.out.println("No pending transactions to mine.");
            return null;
        }

        List<Transaction> toMine = new ArrayList<>(pendingTransactions);
        pendingTransactions.clear();

        int nextIndex = blockchain.size();
        String previousHash = blockchain.getLastBlock().getHash();

        System.out.printf("Mining block %d with %d transaction(s)...%n", nextIndex, toMine.size());
        Block newBlock = blockFactory.createBlock(nextIndex, previousHash, toMine);
        blockchain.addBlock(newBlock);
        System.out.printf("Block mined: %s%n", newBlock);
        return newBlock;
    }

    /**
     * Computes the net balance of an address by scanning all confirmed blocks.
     * (Sent amounts are subtracted; received amounts are added.)
     */
    public double getBalance(String address) {
        double balance = 0.0;
        for (Block block : blockchain.getChain()) {
            for (Transaction tx : block.getTransactions()) {
                if (tx.getSender().equals(address)) balance -= tx.getAmount();
                if (tx.getReceiver().equals(address)) balance += tx.getAmount();
            }
        }
        return balance;
    }

    /**
     * Returns all confirmed transactions involving the given address.
     */
    public List<Transaction> getTransactionHistory(String address) {
        List<Transaction> history = new ArrayList<>();
        for (Block block : blockchain.getChain()) {
            for (Transaction tx : block.getTransactions()) {
                if (tx.getSender().equals(address) || tx.getReceiver().equals(address)) {
                    history.add(tx);
                }
            }
        }
        return history;
    }

    public boolean isChainValid() {
        return validationService.isChainValid(blockchain);
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public List<Transaction> getPendingTransactions() {
        return List.copyOf(pendingTransactions);
    }
}
