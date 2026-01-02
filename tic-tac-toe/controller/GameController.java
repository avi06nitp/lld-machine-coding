package controller;

import enums.GameStatus;
import exception.GameException;
import factory.PlayerFactory;
import model.Board;
import model.GameState;
import model.Player;
import service.GameService;
import service.ValidationService;
import strategy.DefaultWinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private final Scanner scanner;
    private final GameService gameService;
    private final ValidationService validationService;
    private final PlayerFactory playerFactory;

    public GameController() {
        this.scanner = new Scanner(System.in);
        this.gameService = new GameService(new DefaultWinningStrategy());
        this.validationService = new ValidationService();
        this.playerFactory = new PlayerFactory(scanner);
    }

    public void startGame() {
        displayWelcomeMessage();

        try {
            Board board = createBoard();
            List<Player> players = createPlayers();
            GameState gameState = gameService.initializeGame(board, players);

            playGame(gameState);

        } catch (GameException e) {
            System.out.println("\nGame initialization failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private void displayWelcomeMessage() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        WELCOME TO TIC-TAC-TOE GAME");
        System.out.println("=".repeat(50));
    }

    private Board createBoard() throws GameException {
        while (true) {
            try {
                System.out.print("\nEnter board size (3-10): ");
                int size = scanner.nextInt();
                scanner.nextLine();

                validationService.validateBoardSize(size);
                return new Board(size);

            } catch (GameException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private List<Player> createPlayers() throws GameException {
        System.out.print("\nEnter number of players (default 2): ");
        int numPlayers = 2;

        try {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                numPlayers = Integer.parseInt(input);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using default: 2 players");
        }

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            Player player = playerFactory.createPlayer(i);
            players.add(player);
        }

        return players;
    }

    private void playGame(GameState gameState) {
        boolean playAgain = true;

        while (playAgain) {
            playRound(gameState);
            playAgain = askPlayAgain();

            if (playAgain) {
                resetGame(gameState);
            }
        }

        System.out.println("\nThank you for playing! Goodbye!");
    }

    private void playRound(GameState gameState) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("              GAME START");
        System.out.println("=".repeat(50));

        gameState.getBoard().display();

        while (!gameState.isGameOver()) {
            gameService.playTurn(gameState);
            gameState.getBoard().display();
        }

        displayGameResult(gameState);
    }

    private void displayGameResult(GameState gameState) {
        System.out.println("\n" + "=".repeat(50));

        if (gameState.getStatus() == GameStatus.WON) {
            System.out.println("        🎉 " + gameState.getWinner().getName() + " WINS! 🎉");
        } else if (gameState.getStatus() == GameStatus.DRAW) {
            System.out.println("              IT'S A DRAW!");
        }

        System.out.println("=".repeat(50));
        System.out.println("Total moves: " + gameState.getMoveCount());
    }

    private boolean askPlayAgain() {
        while (true) {
            System.out.print("\nPlay again? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y") || response.equals("yes")) {
                return true;
            } else if (response.equals("n") || response.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    private void resetGame(GameState gameState) {
        gameService.resetGame(gameState);
        System.out.println("\nBoard has been reset. Starting new game...");
    }
}
