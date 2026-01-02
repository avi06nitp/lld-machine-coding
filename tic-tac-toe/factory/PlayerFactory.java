package factory;

import enums.PlayerType;
import exception.GameException;
import model.Player;
import service.ValidationService;
import strategy.AIPlayerStrategy;
import strategy.HumanPlayerStrategy;
import strategy.PlayerStrategy;

import java.util.Scanner;

public class PlayerFactory {
    private final Scanner scanner;
    private final ValidationService validationService;

    public PlayerFactory(Scanner scanner) {
        this.scanner = scanner;
        this.validationService = new ValidationService();
    }

    public Player createPlayer(int playerNumber) throws GameException {
        System.out.println("\n=== Player " + playerNumber + " Configuration ===");

        String name = getPlayerName();
        char symbol = getPlayerSymbol();
        PlayerType playerType = getPlayerType();

        PlayerStrategy strategy = createPlayerStrategy(playerType);

        return new Player(name, symbol, playerType, strategy);
    }

    private String getPlayerName() throws GameException {
        while (true) {
            try {
                System.out.print("Enter player name: ");
                String name = scanner.nextLine().trim();
                validationService.validatePlayerName(name);
                return name;
            } catch (GameException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private char getPlayerSymbol() throws GameException {
        while (true) {
            try {
                System.out.print("Enter player symbol (single character): ");
                String input = scanner.nextLine().trim();

                if (input.length() != 1) {
                    throw new GameException("Symbol must be exactly one character");
                }

                char symbol = input.charAt(0);
                validationService.validateSymbol(symbol);
                return symbol;
            } catch (GameException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private PlayerType getPlayerType() {
        while (true) {
            try {
                System.out.print("Player type (1 = Human, 2 = AI): ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    return PlayerType.HUMAN;
                } else if (choice == 2) {
                    return PlayerType.AI;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter 1 or 2.");
                scanner.nextLine();
            }
        }
    }

    private PlayerStrategy createPlayerStrategy(PlayerType playerType) {
        return switch (playerType) {
            case HUMAN -> new HumanPlayerStrategy(scanner);
            case AI -> new AIPlayerStrategy();
        };
    }
}