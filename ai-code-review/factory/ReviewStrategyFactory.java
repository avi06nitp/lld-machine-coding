package factory;

import enums.Language;
import exception.UnsupportedLanguageException;
import strategy.GeneralRuleCheckingStrategy;
import strategy.JavaRuleCheckingStrategy;
import strategy.PythonRuleCheckingStrategy;
import strategy.RuleCheckingStrategy;

/**
 * Factory for creating language-specific rule checking strategies.
 * Centralizes strategy instantiation and decouples callers from concrete implementations.
 */
public class ReviewStrategyFactory {

    /**
     * Creates the appropriate strategy for the given language.
     *
     * @param language the programming language of the code being reviewed
     * @return a RuleCheckingStrategy tailored to that language
     * @throws UnsupportedLanguageException if no specific strategy exists and general fallback is disabled
     */
    public RuleCheckingStrategy createStrategy(Language language) {
        return switch (language) {
            case JAVA -> new JavaRuleCheckingStrategy();
            case PYTHON -> new PythonRuleCheckingStrategy();
            case JAVASCRIPT, TYPESCRIPT, GENERAL -> new GeneralRuleCheckingStrategy();
        };
    }

    /**
     * Creates a strategy from a file extension string.
     */
    public RuleCheckingStrategy createStrategyFromExtension(String extension) {
        Language language = Language.fromExtension(extension);
        return createStrategy(language);
    }
}
