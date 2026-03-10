package command;

import model.CodeSnippet;
import model.ReviewComment;
import strategy.RuleCheckingStrategy;

import java.util.List;

/**
 * Command that checks code complexity metrics using the injected strategy.
 */
public class CodeComplexityCommand implements AnalysisCommand {

    private final RuleCheckingStrategy strategy;

    public CodeComplexityCommand(RuleCheckingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String getCommandName() {
        return "Code Complexity Analysis";
    }

    @Override
    public List<ReviewComment> execute(CodeSnippet snippet) {
        return strategy.checkCodeComplexity(snippet);
    }
}
