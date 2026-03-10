package strategy;

import enums.ReviewCategory;
import enums.Severity;
import model.CodeSnippet;
import model.ReviewComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Language-agnostic rule checking strategy.
 * Checks universal code quality issues regardless of programming language.
 */
public class GeneralRuleCheckingStrategy implements RuleCheckingStrategy {

    private static final int MAX_LINE_LENGTH = 100;
    private static final int TODO_WARN_THRESHOLD = 5;

    @Override
    public String getStrategyName() {
        return "General Rule Checker";
    }

    @Override
    public List<ReviewComment> checkNamingConventions(CodeSnippet snippet) {
        List<ReviewComment> comments = new ArrayList<>();
        List<String> lines = snippet.getLines();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int lineNumber = i + 1;

            if (line.length() > MAX_LINE_LENGTH) {
                comments.add(new ReviewComment(lineNumber,
                        "Line exceeds " + MAX_LINE_LENGTH + " characters (" + line.length() + ")",
                        Severity.INFO, ReviewCategory.NAMING_CONVENTION,
                        "Break long lines for better readability"));
            }
        }

        return comments;
    }

    @Override
    public List<ReviewComment> checkCodeComplexity(CodeSnippet snippet) {
        List<ReviewComment> comments = new ArrayList<>();
        List<String> lines = snippet.getLines();
        int totalLines = lines.size();

        if (totalLines > 300) {
            comments.add(new ReviewComment(0,
                    "File is " + totalLines + " lines long — consider splitting into smaller modules",
                    Severity.WARNING, ReviewCategory.CODE_COMPLEXITY,
                    "A file over 300 lines is often a sign of too many responsibilities (SRP violation)"));
        }

        return comments;
    }

    @Override
    public List<ReviewComment> checkSecurity(CodeSnippet snippet) {
        List<ReviewComment> comments = new ArrayList<>();
        List<String> lines = snippet.getLines();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).toLowerCase();
            int lineNumber = i + 1;

            if (line.matches(".*\\b(password|passwd|secret|apikey|api_key|token)\\s*[=:]\\s*[\"'][^\"']{4,}[\"'].*")) {
                comments.add(new ReviewComment(lineNumber,
                        "Potential hardcoded credential detected",
                        Severity.ERROR, ReviewCategory.SECURITY,
                        "Store sensitive values in environment variables or a secrets manager, never in source code"));
            }
        }

        return comments;
    }

    @Override
    public List<ReviewComment> checkBestPractices(CodeSnippet snippet) {
        List<ReviewComment> comments = new ArrayList<>();
        List<String> lines = snippet.getLines();
        int todoCount = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int lineNumber = i + 1;
            String upper = line.toUpperCase();

            // TODO / FIXME / HACK comments
            if (upper.contains("TODO") || upper.contains("FIXME") || upper.contains("HACK")) {
                todoCount++;
                if (todoCount <= TODO_WARN_THRESHOLD) {
                    comments.add(new ReviewComment(lineNumber,
                            "TODO/FIXME/HACK comment found — unfinished or problematic code",
                            Severity.INFO, ReviewCategory.BEST_PRACTICES,
                            "Track these as issues in your issue tracker and remove the comment once resolved"));
                }
            }

            // Trailing whitespace
            if (!line.isEmpty() && line.charAt(line.length() - 1) == ' ') {
                comments.add(new ReviewComment(lineNumber,
                        "Trailing whitespace detected",
                        Severity.INFO, ReviewCategory.BEST_PRACTICES,
                        "Configure your editor to auto-remove trailing whitespace on save"));
            }

            // Commented-out code (heuristic: lines starting with // or # followed by code-like content)
            String trimmed = line.trim();
            if ((trimmed.startsWith("//") || trimmed.startsWith("#")) &&
                    trimmed.matches(".*[;{}()=].*") && trimmed.length() > 5) {
                comments.add(new ReviewComment(lineNumber,
                        "Commented-out code detected",
                        Severity.INFO, ReviewCategory.BEST_PRACTICES,
                        "Remove dead code — use version control (git) to track history instead of commenting it out"));
            }
        }

        if (todoCount > TODO_WARN_THRESHOLD) {
            comments.add(new ReviewComment(0,
                    "File contains " + todoCount + " TODO/FIXME/HACK comments",
                    Severity.WARNING, ReviewCategory.BEST_PRACTICES,
                    "Address outstanding TODOs before merging to main branch"));
        }

        return comments;
    }
}
