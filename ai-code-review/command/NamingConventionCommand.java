package command;

import model.CodeSnippet;
import model.ReviewComment;
import strategy.RuleCheckingStrategy;

import java.util.List;

/**
 * Command that checks naming conventions using the injected strategy.
 * Delegates to the language-specific strategy for rule checking.
 */
public class NamingConventionCommand implements AnalysisCommand {

    private final RuleCheckingStrategy strategy;

    public NamingConventionCommand(RuleCheckingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String getCommandName() {
        return "Naming Convention Analysis";
    }

    @Override
    public List<ReviewComment> execute(CodeSnippet snippet) {
        return strategy.checkNamingConventions(snippet);
    }
}
