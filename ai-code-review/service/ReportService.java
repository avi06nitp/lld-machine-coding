package service;

import enums.ReviewCategory;
import enums.Severity;
import model.ReviewComment;
import model.ReviewReport;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service responsible for formatting and displaying review reports.
 * Separates presentation logic from the core review logic (SRP).
 */
public class ReportService {

    private static final String DIVIDER = "─".repeat(70);
    private static final String THICK_DIVIDER = "═".repeat(70);

    /**
     * Prints a formatted review report to stdout.
     */
    public void printReport(ReviewReport report) {
        System.out.println();
        System.out.println(THICK_DIVIDER);
        System.out.println("  AI CODE REVIEW REPORT");
        System.out.println(THICK_DIVIDER);
        System.out.printf("  File     : %s%n", report.getFileName());
        System.out.printf("  Duration : %d ms%n", report.getReviewDurationMs());
        System.out.printf("  Issues   : %d errors, %d warnings, %d info%n",
                report.getErrorCount(), report.getWarningCount(), report.getInfoCount());
        System.out.println(THICK_DIVIDER);

        if (report.getComments().isEmpty()) {
            System.out.println();
            System.out.println("  No issues found. Great job!");
            System.out.println();
            System.out.println(THICK_DIVIDER);
            return;
        }

        // Group by category for organized output
        Map<ReviewCategory, List<ReviewComment>> byCategory = report.getComments().stream()
                .collect(Collectors.groupingBy(ReviewComment::getCategory));

        for (ReviewCategory category : ReviewCategory.values()) {
            List<ReviewComment> categoryComments = byCategory.get(category);
            if (categoryComments == null || categoryComments.isEmpty()) continue;

            System.out.println();
            System.out.println("  " + category.getDisplayName().toUpperCase());
            System.out.println(DIVIDER);

            // Sort by severity (errors first) then by line number
            categoryComments.stream()
                    .sorted(Comparator.comparingInt((ReviewComment c) -> -c.getSeverity().getLevel())
                            .thenComparingInt(ReviewComment::getLineNumber))
                    .forEach(comment -> {
                        System.out.printf("  %s%n", comment);
                    });
        }

        System.out.println();
        System.out.println(THICK_DIVIDER);
        printSummary(report);
        System.out.println(THICK_DIVIDER);
        System.out.println();
    }

    /**
     * Prints a concise summary of the review.
     */
    private void printSummary(ReviewReport report) {
        System.out.println("  SUMMARY");
        System.out.println(DIVIDER);
        System.out.printf("  %-12s %d%n", "Errors:", report.getErrorCount());
        System.out.printf("  %-12s %d%n", "Warnings:", report.getWarningCount());
        System.out.printf("  %-12s %d%n", "Info:", report.getInfoCount());
        System.out.printf("  %-12s %d%n", "Total:", report.getComments().size());
        System.out.println();

        if (report.hasCriticalIssues()) {
            System.out.println("  VERDICT: ✗ REVIEW FAILED — Critical issues must be resolved before merging.");
        } else if (report.getWarningCount() > 0) {
            System.out.println("  VERDICT: ⚠ REVIEW PASSED WITH WARNINGS — Please address warnings.");
        } else {
            System.out.println("  VERDICT: ✓ REVIEW PASSED — Code meets quality standards.");
        }
    }
}
