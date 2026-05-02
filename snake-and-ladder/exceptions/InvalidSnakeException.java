package exceptions;

public class InvalidSnakeException extends RuntimeException {
    public InvalidSnakeException(String message) {
        super(message);
    }
}
