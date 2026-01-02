package strategy;

import exception.InvalidMoveException;
import model.Board;
import model.Cell;
import model.Move;
import model.Player;

import java.util.Scanner;

public class HumanPlayerStrategy implements PlayerStrategy {
    private final Scanner scanner;

    public HumanPlayerStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Move makeMove(Player player, Board board) {
        while (true) {
            try {
                System.out.printf("\n%s's turn (Symbol: %c)\n", player.getName(), player.getSymbol());
                System.out.print("Enter row (0-" + (board.getSize() - 1) + "): ");
                int row = scanner.nextInt();
                System.out.print("Enter column (0-" + (board.getSize() - 1) + "): ");
                int col = scanner.nextInt();
                scanner.nextLine();

                validateMove(board, row, col);

                Cell cell = board.getCell(row, col);
                return new Move(player, cell);

            } catch (InvalidMoveException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.\n");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter valid numbers.");
                scanner.nextLine();
            }
        }
    }

    private void validateMove(Board board, int row, int col) throws InvalidMoveException {
        if (!board.isValidPosition(row, col)) {
            throw new InvalidMoveException(String.format(
                "Position (%d, %d) is out of bounds. Valid range: 0-%d",
                row, col, board.getSize() - 1
            ));
        }

        if (!board.isCellEmpty(row, col)) {
            throw new InvalidMoveException(String.format(
                "Cell (%d, %d) is already occupied", row, col
            ));
        }
    }
}