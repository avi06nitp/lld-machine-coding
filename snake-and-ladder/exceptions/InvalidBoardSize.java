package snakeandladder.exceptions;

public class InvalidBoardSize extends RuntimeException {
    public InvalidBoardSize(String message) {
        super(message);
    }
}
