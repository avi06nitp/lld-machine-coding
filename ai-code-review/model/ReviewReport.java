package model;

import enums.Severity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewReport {
    private final String fileName;
    private final List<ReviewComment> comments;
    private final long reviewDurationMs;

    public ReviewReport(String fileName, List<ReviewComment> comments, long reviewDurationMs) {
        this.fileName = fileName;
        this.comments = Collections.unmodifiableList(new ArrayList<>(comments));
        this.reviewDurationMs = reviewDurationMs;
    }

    public String getFileName() {
        return fileName;
    }

    public List<ReviewComment> getComments() {
        return comments;
    }

    public long getReviewDurationMs() {
        return reviewDurationMs;
    }

    public long getErrorCount() {
        return comments.stream().filter(c -> c.getSeverity() == Severity.ERROR).count();
    }

    public long getWarningCount() {
        return comments.stream().filter(c -> c.getSeverity() == Severity.WARNING).count();
    }

    public long getInfoCount() {
        return comments.stream().filter(c -> c.getSeverity() == Severity.INFO).count();
    }

    public boolean hasCriticalIssues() {
        return getErrorCount() > 0;
    }

    public List<ReviewComment> getCommentsBySeverity(Severity severity) {
        return comments.stream()
                .filter(c -> c.getSeverity() == severity)
                .collect(Collectors.toList());
    }
}
