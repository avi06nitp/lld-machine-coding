package exceptions;

public class InvalidPlayerSize extends RuntimeException {
    public InvalidPlayerSize(String message) {
        super(message);
    }
}
