package command;

import model.CodeSnippet;
import model.ReviewComment;

import java.util.List;

/**
 * Command interface for code analysis operations.
 * Each command encapsulates a specific type of analysis (naming, security, etc.)
 * and can be executed, undone (cleared), and queried for its name.
 */
public interface AnalysisCommand {

    /**
     * Returns a human-readable name for this analysis command.
     */
    String getCommandName();

    /**
     * Executes the analysis on the given code snippet and returns findings.
     */
    List<ReviewComment> execute(CodeSnippet snippet);
}
