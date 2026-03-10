package exception;

public class InvalidBlockException extends BlockchainException {
    public InvalidBlockException(String message) {
        super(message);
    }
}
