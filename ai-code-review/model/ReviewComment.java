package model;

import enums.ReviewCategory;
import enums.Severity;

public class ReviewComment {
    private final int lineNumber;
    private final String message;
    private final Severity severity;
    private final ReviewCategory category;
    private final String suggestion;

    public ReviewComment(int lineNumber, String message, Severity severity,
                         ReviewCategory category, String suggestion) {
        this.lineNumber = lineNumber;
        this.message = message;
        this.severity = severity;
        this.category = category;
        this.suggestion = suggestion;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getMessage() {
        return message;
    }

    public Severity getSeverity() {
        return severity;
    }

    public ReviewCategory getCategory() {
        return category;
    }

    public String getSuggestion() {
        return suggestion;
    }

    @Override
    public String toString() {
        String location = lineNumber > 0 ? "Line " + lineNumber : "File";
        return String.format("[%s] [%s] %s: %s%s",
                severity.getLabel(),
                category.getDisplayName(),
                location,
                message,
                suggestion != null && !suggestion.isBlank() ? " → " + suggestion : "");
    }
}
