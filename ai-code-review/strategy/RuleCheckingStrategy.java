package strategy;

import model.CodeSnippet;
import model.ReviewComment;

import java.util.List;

/**
 * Strategy interface for language-specific rule checking.
 * Each implementation encapsulates the rules for a specific language.
 */
public interface RuleCheckingStrategy {

    /**
     * Returns a human-readable name for this strategy.
     */
    String getStrategyName();

    /**
     * Checks naming conventions (class names, method names, variable names).
     */
    List<ReviewComment> checkNamingConventions(CodeSnippet snippet);

    /**
     * Checks for code complexity issues (long methods, deep nesting, etc.).
     */
    List<ReviewComment> checkCodeComplexity(CodeSnippet snippet);

    /**
     * Checks for security vulnerabilities specific to the language.
     */
    List<ReviewComment> checkSecurity(CodeSnippet snippet);

    /**
     * Checks for best practice violations.
     */
    List<ReviewComment> checkBestPractices(CodeSnippet snippet);
}
