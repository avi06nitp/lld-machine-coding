package exception;

public class InvalidMoveException extends GameException {
    public InvalidMoveException(String message) {
        super(message);
    }

    public InvalidMoveException(int row, int col) {
        super(String.format("Invalid move at position (%d, %d)", row, col));
    }
}