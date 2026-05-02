package tictactoe.strategy;

import tictactoe.models.Board;
import tictactoe.models.Move;
import tictactoe.models.Player;
import tictactoe.service.InputValidationService;
import tictactoe.exceptions.InvalidInputExeption;

import java.util.Scanner;

public class HumanMoveStrategy implements MoveStrategy {
    Scanner scanner = new Scanner(System.in);
    private final InputValidationService inputValidationService;

    public HumanMoveStrategy(InputValidationService inputValidationService) {
        this.inputValidationService = inputValidationService;
    }

    @Override
    public void move(Board board, Player player) {
        boolean isValid = false;
        while (!isValid) {
            try {
                Move move = takeMoveInputs(board, player);
                int row = move.getRow();
                int col = move.getCol();
                inputValidationService.validateInput(row, col, board);
                board.setCell(row, col, player.getSymbol());
                isValid = true;
            } catch (InvalidInputExeption e) {
                System.out.println("\033[1;31m  [x] " + e.getMessage() + "\033[0m");
                System.out.println("\033[1;33m  ► Please try again.\033[0m\n");
            } catch (Exception e) {
                System.out.println("\033[1;31m  [x] Unexpected error: " + e.getMessage() + "\033[0m");
                System.out.println("\033[1;33m  ► Please try again.\033[0m\n");
            }
        }
    }

    private Move takeMoveInputs(Board board, Player player) {
        System.out.println("\033[1;33m  ► " + player.getName() + " (" + player.getSymbol() + "), enter move as row,col (0-indexed, e.g. 1,2):\033[0m");
        String input = scanner.nextLine().trim();
        String[] parts = input.split(",");
        if (parts.length != 2) {
            throw new InvalidInputExeption("Input must be in format row,col — e.g. 1,2");
        }
        try {
            int row = Integer.parseInt(parts[0].trim());
            int col = Integer.parseInt(parts[1].trim());
            return new Move(row, col, board);
        } catch (NumberFormatException e) {
            throw new InvalidInputExeption("Row and column must be numbers — e.g. 1,2");
        }
    }
}
