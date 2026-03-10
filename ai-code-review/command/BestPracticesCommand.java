package command;

import model.CodeSnippet;
import model.ReviewComment;
import strategy.RuleCheckingStrategy;

import java.util.List;

/**
 * Command that checks for best practice violations using the injected strategy.
 */
public class BestPracticesCommand implements AnalysisCommand {

    private final RuleCheckingStrategy strategy;

    public BestPracticesCommand(RuleCheckingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String getCommandName() {
        return "Best Practices Analysis";
    }

    @Override
    public List<ReviewComment> execute(CodeSnippet snippet) {
        return strategy.checkBestPractices(snippet);
    }
}
