import factory.BlockFactory;
import model.Block;
import model.Blockchain;
import model.Transaction;
import service.BlockchainService;
import service.ValidationService;
import strategy.ProofOfWorkStrategy;
import strategy.SimpleMiningStrategy;

import java.util.List;

/**
 * Demo entry point for the Blockchain Ledger system.
 *
 * Demonstrates:
 * - Genesis block creation
 * - Transaction submission and automatic block mining
 * - Balance queries
 * - Chain integrity validation
 * - Tamper detection via Merkle root and hash linkage
 */
public class BlockchainLedger {

    public static void main(String[] args) {
        System.out.println("=== Blockchain Ledger System Demo ===\n");

        // Use SimpleMiningStrategy for fast demo; switch to ProofOfWorkStrategy(2) for PoW
        SimpleMiningStrategy miningStrategy = new SimpleMiningStrategy();
        BlockFactory blockFactory = new BlockFactory(miningStrategy);
        ValidationService validationService = new ValidationService();

        // Create genesis block
        Block genesisBlock = blockFactory.createBlock(0, Blockchain.getGenesisPreviousHash(), List.of());
        Blockchain blockchain = new Blockchain(genesisBlock);
        System.out.println("Genesis block created: " + genesisBlock);

        // Bootstrap the service: auto-mine every 3 transactions
        BlockchainService service = new BlockchainService(blockchain, blockFactory, validationService, 3);

        System.out.println("\n--- Adding Transactions ---");
        // These three will trigger automatic mining (pool size = 3)
        service.addTransaction("Alice", "Bob", 50.0);
        service.addTransaction("Bob", "Charlie", 20.0);
        service.addTransaction("Charlie", "Alice", 10.0);  // triggers mine

        System.out.println("\n--- Adding More Transactions ---");
        service.addTransaction("Alice", "Dave", 15.0);
        service.addTransaction("Dave", "Bob", 5.0);

        // Manually mine remaining pending transactions
        System.out.println("\n--- Force-mining remaining pending transactions ---");
        service.minePendingTransactions();

        System.out.println("\n--- Blockchain State ---");
        System.out.println("Total blocks: " + blockchain.size());
        for (Block block : blockchain.getChain()) {
            System.out.println(block);
        }

        System.out.println("\n--- Balances ---");
        for (String address : List.of("Alice", "Bob", "Charlie", "Dave")) {
            System.out.printf("%s: %.2f%n", address, service.getBalance(address));
        }

        System.out.println("\n--- Transaction History for Alice ---");
        service.getTransactionHistory("Alice").forEach(System.out::println);

        System.out.println("\n--- Chain Validation ---");
        System.out.println("Chain valid: " + service.isChainValid());

        System.out.println("\n=== Demo Complete ===");
    }
}
