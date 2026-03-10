package strategy;

import enums.ReviewCategory;
import enums.Severity;
import model.CodeSnippet;
import model.ReviewComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Python-specific rule checking strategy.
 * Enforces PEP 8 style guidelines and Python best practices.
 */
public class PythonRuleCheckingStrategy implements RuleCheckingStrategy {

    private static final int MAX_LINE_LENGTH = 79; // PEP 8 standard
    private static final int MAX_FUNCTION_LENGTH = 40;
    private static final int MAX_PARAMETERS = 5;

    @Override
    public String getStrategyName() {
        return "Python (PEP 8) Rule Checker";
    }

    @Override
    public List<ReviewComment> checkNamingConventions(CodeSnippet snippet) {
        List<ReviewComment> comments = new ArrayList<>();
        List<String> lines = snippet.getLines();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int lineNumber = i + 1;
            String trimmed = line.trim();

            // Class names should be PascalCase
            if (trimmed.startsWith("class ")) {
                String className = trimmed.replaceFirst("class\\s+(\\w+).*", "$1");
                if (!className.isEmpty() && !Character.isUpperCase(className.charAt(0))) {
                    comments.add(new ReviewComment(lineNumber,
                            "Class name '" + className + "' should use PascalCase",
                            Severity.ERROR, ReviewCategory.NAMING_CONVENTION,
                            "Rename to '" + Character.toUpperCase(className.charAt(0)) + className.substring(1) + "'"));
                }
            }

            // Function names should be snake_case
            if (trimmed.startsWith("def ")) {
                String funcName = trimmed.replaceFirst("def\\s+(\\w+).*", "$1");
                if (!funcName.isEmpty() && (funcName.contains("-") || funcName.matches(".*[A-Z].*"))) {
                    comments.add(new ReviewComment(lineNumber,
                            "Function name '" + funcName + "' should use snake_case (PEP 8)",
                            Severity.WARNING, ReviewCategory.NAMING_CONVENTION,
                            "Use lowercase letters with underscores, e.g., my_function_name"));
                }
            }

            // Line length (PEP 8: max 79 chars)
            if (line.length() > MAX_LINE_LENGTH) {
                comments.add(new ReviewComment(lineNumber,
                        "Line exceeds PEP 8 limit of " + MAX_LINE_LENGTH + " characters (" + line.length() + ")",
                        Severity.INFO, ReviewCategory.NAMING_CONVENTION,
                        "Break into multiple lines; use parentheses for implicit line continuation"));
            }
        }

        return comments;
    }

    @Override
    public List<ReviewComment> checkCodeComplexity(CodeSnippet snippet) {
        List<ReviewComment> comments = new ArrayList<>();
        List<String> lines = snippet.getLines();
        int functionStart = -1;
        int functionLineCount = 0;
        int baseIndent = -1;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int lineNumber = i + 1;
            String trimmed = line.trim();

            if (trimmed.startsWith("def ")) {
                if (functionStart >= 0 && functionLineCount > MAX_FUNCTION_LENGTH) {
                    comments.add(new ReviewComment(functionStart,
                            "Function is " + functionLineCount + " lines long, exceeding maximum of " + MAX_FUNCTION_LENGTH,
                            Severity.WARNING, ReviewCategory.CODE_COMPLEXITY,
                            "Break into smaller helper functions following the Single Responsibility Principle"));
                }
                functionStart = lineNumber;
                functionLineCount = 0;
                baseIndent = line.indexOf("def");

                // Check parameter count
                String params = trimmed.replaceFirst("def\\s+\\w+\\((.*)\\).*", "$1").trim();
                if (!params.isEmpty() && !params.equals(trimmed)) {
                    long paramCount = params.split(",").length;
                    if (paramCount > MAX_PARAMETERS) {
                        comments.add(new ReviewComment(lineNumber,
                                "Function has " + paramCount + " parameters, exceeding maximum of " + MAX_PARAMETERS,
                                Severity.WARNING, ReviewCategory.CODE_COMPLEXITY,
                                "Consider grouping related parameters into a dictionary or dataclass"));
                    }
                }
            }

            if (functionStart >= 0) {
                functionLineCount++;
            }

            // Deep nesting detection via indentation level
            int indent = 0;
            for (char c : line.toCharArray()) {
                if (c == ' ') indent++;
                else if (c == '\t') indent += 4;
                else break;
            }
            if (!trimmed.isEmpty() && indent >= (baseIndent >= 0 ? baseIndent + 16 : 16)) {
                comments.add(new ReviewComment(lineNumber,
                        "Deep nesting detected (indentation level " + (indent / 4) + ")",
                        Severity.WARNING, ReviewCategory.CODE_COMPLEXITY,
                        "Refactor nested logic using early returns, guard clauses, or helper functions"));
            }
        }

        return comments;
    }

    @Override
    public List<ReviewComment> checkSecurity(CodeSnippet snippet) {
        List<ReviewComment> comments = new ArrayList<>();
        List<String> lines = snippet.getLines();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            int lineNumber = i + 1;

            // eval() usage
            if (line.contains("eval(") && !line.startsWith("#")) {
                comments.add(new ReviewComment(lineNumber,
                        "eval() detected — can execute arbitrary code",
                        Severity.ERROR, ReviewCategory.SECURITY,
                        "Avoid eval(); use ast.literal_eval() for safe expression parsing, or refactor the logic"));
            }

            // exec() usage
            if (line.contains("exec(") && !line.startsWith("#")) {
                comments.add(new ReviewComment(lineNumber,
                        "exec() detected — can execute arbitrary code",
                        Severity.ERROR, ReviewCategory.SECURITY,
                        "Avoid exec(); find a safer alternative specific to your use case"));
            }

            // Hardcoded passwords
            if (line.toLowerCase().matches(".*\\b(password|passwd|secret|api_?key|token)\\s*=\\s*[\"'][^\"']+[\"'].*")) {
                comments.add(new ReviewComment(lineNumber,
                        "Potential hardcoded credential detected",
                        Severity.ERROR, ReviewCategory.SECURITY,
                        "Use environment variables (os.environ) or a secrets manager"));
            }

            // pickle deserialization
            if (line.contains("pickle.load") || line.contains("pickle.loads")) {
                comments.add(new ReviewComment(lineNumber,
                        "Unsafe deserialization with pickle — can execute arbitrary code",
                        Severity.ERROR, ReviewCategory.SECURITY,
                        "Use JSON or another safe serialization format for untrusted data"));
            }

            // SQL string formatting
            if ((line.contains("execute(") || line.contains("executemany(")) &&
                    (line.contains("%") || line.contains(".format(") || line.contains("f\""))) {
                comments.add(new ReviewComment(lineNumber,
                        "Potential SQL injection: string formatting used in SQL query",
                        Severity.ERROR, ReviewCategory.SECURITY,
                        "Use parameterized queries: cursor.execute(sql, params) where params is a tuple"));
            }
        }

        return comments;
    }

    @Override
    public List<ReviewComment> checkBestPractices(CodeSnippet snippet) {
        List<ReviewComment> comments = new ArrayList<>();
        List<String> lines = snippet.getLines();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            int lineNumber = i + 1;

            // Bare except
            if (line.equals("except:") || line.startsWith("except: ")) {
                comments.add(new ReviewComment(lineNumber,
                        "Bare except clause catches all exceptions including KeyboardInterrupt and SystemExit",
                        Severity.ERROR, ReviewCategory.BEST_PRACTICES,
                        "Catch specific exceptions: except ValueError, TypeError: or at least except Exception:"));
            }

            // Mutable default arguments
            if (line.matches("def\\s+\\w+\\(.*=\\s*(\\[|\\{).*\\).*:")) {
                comments.add(new ReviewComment(lineNumber,
                        "Mutable default argument detected (list or dict)",
                        Severity.ERROR, ReviewCategory.BEST_PRACTICES,
                        "Use None as default and initialize inside the function: def f(x=None): if x is None: x = []"));
            }

            // Global variable modification
            if (line.startsWith("global ")) {
                comments.add(new ReviewComment(lineNumber,
                        "Use of 'global' keyword modifies global state",
                        Severity.WARNING, ReviewCategory.BEST_PRACTICES,
                        "Avoid global state; pass values as parameters or use class attributes"));
            }

            // Type comparison with == instead of isinstance
            if (line.matches(".*type\\(.*\\)\\s*==.*")) {
                comments.add(new ReviewComment(lineNumber,
                        "Type comparison using type() == instead of isinstance()",
                        Severity.INFO, ReviewCategory.BEST_PRACTICES,
                        "Use isinstance() to properly handle inheritance: isinstance(obj, MyClass)"));
            }

            // Missing docstring (first line of function/class body)
            if (i > 0) {
                String prevLine = lines.get(i - 1).trim();
                if ((prevLine.startsWith("def ") || prevLine.startsWith("class ")) &&
                        !line.startsWith("\"\"\"") && !line.startsWith("'''") && !line.startsWith("#")) {
                    comments.add(new ReviewComment(lineNumber - 1,
                            "Missing docstring for function/class",
                            Severity.INFO, ReviewCategory.BEST_PRACTICES,
                            "Add a docstring to describe purpose, parameters, and return value"));
                }
            }
        }

        return comments;
    }
}
