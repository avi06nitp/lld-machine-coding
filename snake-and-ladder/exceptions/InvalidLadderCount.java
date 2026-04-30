package exceptions;

public class InvalidLadderCount extends RuntimeException {
    public InvalidLadderCount(String message) {
        super(message);
    }
}
