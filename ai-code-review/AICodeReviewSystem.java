import controller.CodeReviewController;
import service.CodeReviewService;
import service.ReportService;

/**
 * Entry point for the AI-Based Code Review System.
 *
 * Design Patterns Used:
 *   - Command Pattern  : Each analysis type (naming, complexity, security, best practices)
 *                        is encapsulated as a Command. New analysis types can be added
 *                        without modifying existing code (Open/Closed Principle).
 *   - Strategy Pattern : Language-specific rule sets (Java, Python, General) are swappable
 *                        strategies. The same commands work with any language strategy.
 *   - Factory Pattern  : ReviewStrategyFactory creates the correct strategy for a given
 *                        language without exposing creation logic to callers.
 *
 * SOLID Principles:
 *   - SRP : Each class has one job (analysis, reporting, input handling are separate).
 *   - OCP : Add new languages by implementing RuleCheckingStrategy; add new analysis
 *           types by implementing AnalysisCommand — no existing code changes needed.
 *   - LSP : Any RuleCheckingStrategy implementation is interchangeable.
 *   - ISP : Focused interfaces (RuleCheckingStrategy, AnalysisCommand) with minimal methods.
 *   - DIP : CodeReviewService depends on the RuleCheckingStrategy interface, not concretions.
 */
public class AICodeReviewSystem {

    public static void main(String[] args) {
        CodeReviewService reviewService = new CodeReviewService();
        ReportService reportService = new ReportService();
        CodeReviewController controller = new CodeReviewController(reviewService, reportService);
        controller.start();
    }
}
