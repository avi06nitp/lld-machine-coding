package exception;

public class UnsupportedLanguageException extends CodeReviewException {
    public UnsupportedLanguageException(String language) {
        super("Unsupported language for rule checking: " + language);
    }
}
