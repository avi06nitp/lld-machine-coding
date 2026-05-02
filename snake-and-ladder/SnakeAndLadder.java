import snakeandladder.controller.GameController;
import snakeandladder.enums.GameWinningStrategy;
import snakeandladder.exceptions.InvalidBoardSize;
import snakeandladder.exceptions.InvalidLadderCount;
import snakeandladder.exceptions.InvalidPlayerSize;
import snakeandladder.factory.PlayerFactory;
import snakeandladder.factory.WinningStrategyFactory;
import snakeandladder.models.Board;
import snakeandladder.service.ValidationService;
import snakeandladder.strategy.WinningStrategy;

import java.util.Scanner;

public class SnakeAndLadder {
    public static void main(String[] args) {

        ValidationService service = new ValidationService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\033[1;36m");
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║           SNAKE AND LADDER GAME              ║");
        System.out.println("  ╚══════════════════════════════════════════════╝\033[0m");

        int boardSize = 0;
        while (true) {
            try {
                System.out.println("\033[1;33m  ► Enter Board size [100-500] (default = 100):\033[0m");
                boardSize = Integer.parseInt(scanner.nextLine().trim());
                service.validateBoardSize(boardSize);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\033[1;31m  [x] Invalid input — board size must be a number.\033[0m");
            } catch (InvalidBoardSize e) {
                System.out.println("\033[1;31m  [x] " + e.getMessage() + "\033[0m");
            }
        }

        Board board = new Board(boardSize, service);

        int playerSize = 0;
        while (true) {
            try {
                System.out.println("\033[1;33m  ► Enter no. of Players [2-5]:\033[0m");
                playerSize = Integer.parseInt(scanner.nextLine().trim());
                service.validatePlayerSize(playerSize);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\033[1;31m  [x] Invalid input — player count must be a number.\033[0m");
            } catch (InvalidPlayerSize e) {
                System.out.println("\033[1;31m  [x] " + e.getMessage() + "\033[0m");
            }
        }

        int ladderSize = 0;
        while (true) {
            try {
                System.out.println("\033[1;33m  ► Enter no. of Ladders [2-5]:\033[0m");
                ladderSize = Integer.parseInt(scanner.nextLine().trim());
                service.validateSnakeandLadderCountSize(ladderSize);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\033[1;31m  [x] Invalid input — ladder count must be a number.\033[0m");
            } catch (InvalidLadderCount e) {
                System.out.println("\033[1;31m  [x] " + e.getMessage() + "\033[0m");
            }
        }

        int snakeSize = 0;
        while (true) {
            try {
                System.out.println("\033[1;33m  ► Enter no. of Snakes [2-5]:\033[0m");
                snakeSize = Integer.parseInt(scanner.nextLine().trim());
                service.validateSnakeandLadderCountSize(snakeSize);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\033[1;31m  [x] Invalid input — snake count must be a number.\033[0m");
            } catch (InvalidLadderCount e) {
                System.out.println("\033[1;31m  [x] " + e.getMessage() + "\033[0m");
            }
        }

        PlayerFactory factory = new PlayerFactory();
        WinningStrategyFactory winningStrategyFactory = new WinningStrategyFactory();
        GameWinningStrategy gameWinningStrategy = GameWinningStrategy.EXACT;
        while (true) {
            try {
                System.out.println("\033[1;33m  ► Enter winning strategy \033[2m[EXACT / EXCEEDS]\033[0m\033[1;33m:\033[0m");
                gameWinningStrategy = GameWinningStrategy.valueOf(scanner.nextLine().trim().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("\033[1;31m  [x] Invalid strategy — use EXACT or EXCEEDS.\033[0m");
            }
        }

        WinningStrategy winningStrategy = winningStrategyFactory.createWinningStrategy(gameWinningStrategy);
        GameController gameController = new GameController(board, ladderSize, playerSize, snakeSize, factory, winningStrategy, scanner);
        gameController.initializeGame();
        gameController.play();

        scanner.close();
    }
}
