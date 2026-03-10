package command;

import model.CodeSnippet;
import model.ReviewComment;
import strategy.RuleCheckingStrategy;

import java.util.List;

/**
 * Command that checks for security vulnerabilities using the injected strategy.
 */
public class SecurityAnalysisCommand implements AnalysisCommand {

    private final RuleCheckingStrategy strategy;

    public SecurityAnalysisCommand(RuleCheckingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String getCommandName() {
        return "Security Vulnerability Analysis";
    }

    @Override
    public List<ReviewComment> execute(CodeSnippet snippet) {
        return strategy.checkSecurity(snippet);
    }
}
