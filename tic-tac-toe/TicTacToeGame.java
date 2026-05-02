import tictactoe.enums.PlayerType;
import tictactoe.exceptions.InvalidBoardSize;
import tictactoe.factory.PlayerFactory;
import tictactoe.models.Board;
import tictactoe.models.Player;
import tictactoe.service.BoardValidation;
import tictactoe.service.GameService;
import tictactoe.service.InputValidationService;
import tictactoe.service.WinnerDetectionService;

import java.util.Scanner;

public class TicTacToeGame {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\033[1;36m");
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║              TIC-TAC-TOE GAME                ║");
        System.out.println("  ╚══════════════════════════════════════════════╝\033[0m");

        while (true) {
            BoardValidation boardValidation = new BoardValidation();
            Board board;
            int boardSize;
            while (true) {
                try {
                    System.out.println("\033[1;33m  ► Enter board size (3 to 9):\033[0m");
                    boardSize = Integer.parseInt(scanner.nextLine().trim());
                    boardValidation.validBoard(boardSize);
                    board = new Board(boardSize);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("\033[1;31m  [x] Invalid input — board size must be a number.\033[0m");
                } catch (InvalidBoardSize e) {
                    System.out.println("\033[1;31m  [x] Invalid board size — must be between 3 and 9.\033[0m");
                }
            }

            PlayerFactory playerFactory = new PlayerFactory();
            InputValidationService inputValidationService = new InputValidationService();

            System.out.println("\033[1;34m\n  ── Player 1 Setup ──\033[0m");
            System.out.println("\033[1;33m  ► Enter name for Player 1:\033[0m");
            String player1Name = scanner.nextLine().trim();

            char symbol1;
            while (true) {
                System.out.println("\033[1;33m  ► Enter symbol for " + player1Name + ":\033[0m");
                String symbolInput = scanner.nextLine().trim();
                if (symbolInput.isEmpty()) {
                    System.out.println("\033[1;31m  [x] Symbol cannot be empty.\033[0m");
                    continue;
                }
                symbol1 = symbolInput.charAt(0);
                break;
            }

            PlayerType playerType1;
            while (true) {
                try {
                    System.out.println("\033[1;33m  ► Enter " + player1Name + "'s type \033[2m[HUMAN / COMPUTER]\033[0m\033[1;33m:\033[0m");
                    playerType1 = PlayerType.valueOf(scanner.nextLine().trim().toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("\033[1;31m  [x] Invalid type — use HUMAN or COMPUTER.\033[0m");
                }
            }

            System.out.println("\033[1;34m\n  ── Player 2 Setup ──\033[0m");
            System.out.println("\033[1;33m  ► Enter name for Player 2:\033[0m");
            String player2Name = scanner.nextLine().trim();

            char symbol2;
            while (true) {
                System.out.println("\033[1;33m  ► Enter symbol for " + player2Name + ":\033[0m");
                String symbolInput = scanner.nextLine().trim();
                if (symbolInput.isEmpty()) {
                    System.out.println("\033[1;31m  [x] Symbol cannot be empty.\033[0m");
                    continue;
                }
                symbol2 = symbolInput.charAt(0);
                break;
            }

            PlayerType playerType2;
            while (true) {
                try {
                    System.out.println("\033[1;33m  ► Enter " + player2Name + "'s type \033[2m[HUMAN / COMPUTER]\033[0m\033[1;33m:\033[0m");
                    playerType2 = PlayerType.valueOf(scanner.nextLine().trim().toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("\033[1;31m  [x] Invalid type — use HUMAN or COMPUTER.\033[0m");
                }
            }

            WinnerDetectionService winnerDetectionService = new WinnerDetectionService(board);
            GameService gameService = new GameService(winnerDetectionService);

            Player player1 = playerFactory.createPlayer(player1Name, symbol1, playerType1, inputValidationService, symbol2);
            Player player2 = playerFactory.createPlayer(player2Name, symbol2, playerType2, inputValidationService, symbol1);

            gameService.play(player1, player2, board);

            System.out.println("\033[1;33m  ► Play again? \033[2m[Y/N]\033[0m\033[1;33m:\033[0m");
            String answer = scanner.nextLine().trim();
            if (answer.equalsIgnoreCase("N")) {
                System.out.println("\033[1;36m  Thanks for playing!\033[0m");
                break;
            }
        }

        scanner.close();
    }
}