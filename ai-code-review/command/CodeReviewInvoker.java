package command;

import model.CodeSnippet;
import model.ReviewComment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Invoker in the Command Pattern.
 * Manages the queue of analysis commands and executes them in order.
 * Collects results from all commands into a unified list of review comments.
 */
public class CodeReviewInvoker {

    private final List<AnalysisCommand> commands;

    public CodeReviewInvoker() {
        this.commands = new ArrayList<>();
    }

    /**
     * Registers a command to be executed during review.
     */
    public void addCommand(AnalysisCommand command) {
        commands.add(command);
    }

    /**
     * Clears all registered commands.
     */
    public void clearCommands() {
        commands.clear();
    }

    /**
     * Executes all registered commands against the given snippet.
     * Returns a map of command name → findings for transparency.
     */
    public Map<String, List<ReviewComment>> executeAll(CodeSnippet snippet) {
        Map<String, List<ReviewComment>> results = new LinkedHashMap<>();
        for (AnalysisCommand command : commands) {
            List<ReviewComment> findings = command.execute(snippet);
            results.put(command.getCommandName(), Collections.unmodifiableList(findings));
        }
        return Collections.unmodifiableMap(results);
    }

    /**
     * Executes all commands and flattens results into a single list.
     */
    public List<ReviewComment> executeAllFlat(CodeSnippet snippet) {
        List<ReviewComment> allComments = new ArrayList<>();
        for (AnalysisCommand command : commands) {
            allComments.addAll(command.execute(snippet));
        }
        return allComments;
    }

    public int getCommandCount() {
        return commands.size();
    }
}
