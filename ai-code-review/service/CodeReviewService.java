package service;

import command.BestPracticesCommand;
import command.CodeComplexityCommand;
import command.CodeReviewInvoker;
import command.NamingConventionCommand;
import command.SecurityAnalysisCommand;
import exception.CodeReviewException;
import factory.ReviewStrategyFactory;
import model.CodeSnippet;
import model.ReviewComment;
import model.ReviewReport;
import strategy.RuleCheckingStrategy;

import java.util.List;

/**
 * Core service that orchestrates the code review process.
 * Wires together the Command invoker, Strategy, and Factory to produce a ReviewReport.
 */
public class CodeReviewService {

    private final ReviewStrategyFactory strategyFactory;

    public CodeReviewService() {
        this.strategyFactory = new ReviewStrategyFactory();
    }

    /**
     * Performs a full code review on the given snippet and returns a report.
     *
     * @param snippet the code to review
     * @return a ReviewReport containing all findings
     */
    public ReviewReport review(CodeSnippet snippet) {
        if (snippet == null) {
            throw new CodeReviewException("Cannot review a null code snippet");
        }

        long startTime = System.currentTimeMillis();

        RuleCheckingStrategy strategy = strategyFactory.createStrategy(snippet.getLanguage());
        CodeReviewInvoker invoker = buildInvoker(strategy);

        List<ReviewComment> comments = invoker.executeAllFlat(snippet);

        long duration = System.currentTimeMillis() - startTime;
        return new ReviewReport(snippet.getFileName(), comments, duration);
    }

    /**
     * Builds the invoker with all analysis commands for a full review.
     * Adding a new analysis type only requires adding a new command here — OCP.
     */
    private CodeReviewInvoker buildInvoker(RuleCheckingStrategy strategy) {
        CodeReviewInvoker invoker = new CodeReviewInvoker();
        invoker.addCommand(new NamingConventionCommand(strategy));
        invoker.addCommand(new CodeComplexityCommand(strategy));
        invoker.addCommand(new SecurityAnalysisCommand(strategy));
        invoker.addCommand(new BestPracticesCommand(strategy));
        return invoker;
    }
}
