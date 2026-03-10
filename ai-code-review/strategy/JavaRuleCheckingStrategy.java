package strategy;

import enums.ReviewCategory;
import enums.Severity;
import model.CodeSnippet;
import model.ReviewComment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java-specific rule checking strategy.
 * Enforces Java coding standards, naming conventions, and best practices.
 */
public class JavaRuleCheckingStrategy implements RuleCheckingStrategy {

    private static final int MAX_METHOD_LENGTH = 30;
    private static final int MAX_LINE_LENGTH = 120;
    private static final int MAX_NESTING_DEPTH = 4;
    private static final int MAX_PARAMETERS = 5;

    @Override
    public String getStrategyName() {
        return "Java Rule Checker";
    }

    @Override
    public List<ReviewComment> checkNamingConventions(CodeSnippet snippet) {
        List<ReviewComment> comments = new ArrayList<>();
        List<String> lines = snippet.getLines();

        Pattern classPattern = Pattern.compile("\\b(class|interface|enum)\\s+([a-zA-Z_$][\\w$]*)");
        Pattern methodPattern = Pattern.compile("\\b(?:public|private|protected|static|final|void|\\w+)\\s+([a-z][\\w$]*)\\s*\\(");
        Pattern constantPattern = Pattern.compile("\\bstatic\\s+final\\s+\\w+\\s+([a-zA-Z_$][\\w$]*)");
        Pattern variablePattern = Pattern.compile("\\b(?:int|long|double|float|boolean|String|var)\\s+([A-Z][\\w$]*)\\s*[=;,)]");

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            int lineNumber = i + 1;

            // Class names must be PascalCase
            Matcher classMatcher = classPattern.matcher(line);
            while (classMatcher.find()) {
                String name = classMatcher.group(2);
                if (!Character.isUpperCase(name.charAt(0))) {
                    comments.add(new ReviewComment(lineNumber,
                            "Class/interface name '" + name + "' should start with an uppercase letter (PascalCase)",
                            Severity.ERROR, ReviewCategory.NAMING_CONVENTION,
                            "Rename to '" + Character.toUpperCase(name.charAt(0)) + name.substring(1) + "'"));
                }
            }

            // Constants must be UPPER_SNAKE_CASE
            Matcher constantMatcher = constantPattern.matcher(line);
            while (constantMatcher.find()) {
                String name = constantMatcher.group(1);
                if (!name.equals(name.toUpperCase())) {
                    comments.add(new ReviewComment(lineNumber,
                            "Constant '" + name + "' should be UPPER_SNAKE_CASE",
                            Severity.WARNING, ReviewCategory.NAMING_CONVENTION,
                            "Rename to '" + toUpperSnakeCase(name) + "'"));
                }
            }

            // Variables must be camelCase (not starting with uppercase)
            Matcher varMatcher = variablePattern.matcher(line);
            while (varMatcher.find()) {
                String name = varMatcher.group(1);
                comments.add(new ReviewComment(lineNumber,
                        "Variable name '" + name + "' should start with a lowercase letter (camelCase)",
                        Severity.WARNING, ReviewCategory.NAMING_CONVENTION,
                        "Rename to '" + Character.toLowerCase(name.charAt(0)) + name.substring(1) + "'"));
            }

            // Line length
            if (lines.get(i).length() > MAX_LINE_LENGTH) {
                comments.add(new ReviewComment(lineNumber,
                        "Line exceeds maximum length of " + MAX_LINE_LENGTH + " characters (" + lines.get(i).length() + ")",
                        Severity.INFO, ReviewCategory.NAMING_CONVENTION,
                        "Break into multiple lines for readability"));
            }
        }

        return comments;
    }

    @Override
    public List<ReviewComment> checkCodeComplexity(CodeSnippet snippet) {
        List<ReviewComment> comments = new ArrayList<>();
        List<String> lines = snippet.getLines();

        int methodStartLine = -1;
        int methodLineCount = 0;
        int maxNesting = 0;
        int currentNesting = 0;
        boolean inMethod = false;
        int paramCount = 0;

        Pattern methodSignature = Pattern.compile("\\b(?:public|private|protected).*\\((.*)\\)[^;]*$");
        Pattern openBrace = Pattern.compile("\\{");
        Pattern closeBrace = Pattern.compile("\\}");

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            int lineNumber = i + 1;

            // Detect method start and count parameters
            Matcher methodMatcher = methodSignature.matcher(line);
            if (methodMatcher.find()) {
                String params = methodMatcher.group(1).trim();
                if (!params.isEmpty()) {
                    paramCount = params.split(",").length;
                    if (paramCount > MAX_PARAMETERS) {
                        comments.add(new ReviewComment(lineNumber,
                                "Method has " + paramCount + " parameters, exceeding maximum of " + MAX_PARAMETERS,
                                Severity.WARNING, ReviewCategory.CODE_COMPLEXITY,
                                "Consider using a parameter object or builder pattern to reduce parameter count"));
                    }
                }
                if (!inMethod) {
                    inMethod = true;
                    methodStartLine = lineNumber;
                    methodLineCount = 0;
                    maxNesting = 0;
                    currentNesting = 0;
                }
            }

            if (inMethod) {
                methodLineCount++;

                // Count nesting depth
                long opens = line.chars().filter(c -> c == '{').count();
                long closes = line.chars().filter(c -> c == '}').count();
                currentNesting += (int)(opens - closes);
                maxNesting = Math.max(maxNesting, currentNesting);

                // Method too long
                if (methodLineCount > MAX_METHOD_LENGTH && line.equals("}") && currentNesting == 0) {
                    comments.add(new ReviewComment(methodStartLine,
                            "Method is " + methodLineCount + " lines long, exceeding maximum of " + MAX_METHOD_LENGTH,
                            Severity.WARNING, ReviewCategory.CODE_COMPLEXITY,
                            "Extract logic into smaller, focused methods following Single Responsibility Principle"));
                    inMethod = false;
                }

                if (currentNesting < 0) {
                    inMethod = false;
                }
            }

            // Detect deep nesting inline
            if (currentNesting >= MAX_NESTING_DEPTH) {
                comments.add(new ReviewComment(lineNumber,
                        "Nesting depth of " + currentNesting + " detected, exceeding maximum of " + MAX_NESTING_DEPTH,
                        Severity.WARNING, ReviewCategory.CODE_COMPLEXITY,
                        "Consider extracting nested logic into separate methods or using guard clauses"));
            }
        }

        return comments;
    }

    @Override
    public List<ReviewComment> checkSecurity(CodeSnippet snippet) {
        List<ReviewComment> comments = new ArrayList<>();
        List<String> lines = snippet.getLines();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int lineNumber = i + 1;

            // SQL Injection
            if (line.contains("Statement") && (line.contains("execute") || line.contains("query")) &&
                    line.contains("+")) {
                comments.add(new ReviewComment(lineNumber,
                        "Potential SQL injection: string concatenation used in SQL query",
                        Severity.ERROR, ReviewCategory.SECURITY,
                        "Use PreparedStatement with parameterized queries instead of string concatenation"));
            }

            // Hardcoded passwords/secrets
            if (line.toLowerCase().matches(".*\\b(password|passwd|secret|api_?key|token)\\s*=\\s*\"[^\"]+\".*")) {
                comments.add(new ReviewComment(lineNumber,
                        "Potential hardcoded credential detected",
                        Severity.ERROR, ReviewCategory.SECURITY,
                        "Use environment variables or a secrets manager instead of hardcoding credentials"));
            }

            // printStackTrace exposes internals
            if (line.contains("printStackTrace()")) {
                comments.add(new ReviewComment(lineNumber,
                        "printStackTrace() exposes internal stack trace to users",
                        Severity.WARNING, ReviewCategory.SECURITY,
                        "Use a logging framework (SLF4J/Logback) and avoid exposing stack traces in production"));
            }

            // Unsafe deserialization
            if (line.contains("ObjectInputStream") && line.contains("readObject")) {
                comments.add(new ReviewComment(lineNumber,
                        "Unsafe deserialization detected: readObject() can execute arbitrary code",
                        Severity.ERROR, ReviewCategory.SECURITY,
                        "Validate and sanitize serialized data; consider using JSON/XML serialization instead"));
            }

            // System.exit in non-main code
            if (line.contains("System.exit(") && !line.contains("//")) {
                comments.add(new ReviewComment(lineNumber,
                        "System.exit() called — can terminate the entire JVM unexpectedly",
                        Severity.WARNING, ReviewCategory.SECURITY,
                        "Throw an exception or return an error code instead; only use System.exit() in main()"));
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

            // Raw type usage (e.g., List list instead of List<T>)
            if (line.matches(".*\\b(List|Map|Set|Collection|ArrayList|HashMap|HashSet)\\s+\\w+.*") &&
                    !line.contains("<")) {
                comments.add(new ReviewComment(lineNumber,
                        "Raw type used — missing generic type parameter",
                        Severity.WARNING, ReviewCategory.BEST_PRACTICES,
                        "Use parameterized types, e.g., List<String> instead of List, to ensure type safety"));
            }

            // Empty catch block
            if (line.equals("} catch (Exception e) {") || line.equals("} catch (Throwable t) {")) {
                String nextLine = (i + 1 < lines.size()) ? lines.get(i + 1).trim() : "";
                if (nextLine.equals("}")) {
                    comments.add(new ReviewComment(lineNumber,
                            "Empty catch block — exception is swallowed silently",
                            Severity.ERROR, ReviewCategory.BEST_PRACTICES,
                            "Log the exception or handle it appropriately; never silently swallow exceptions"));
                }
            }

            // Catching generic Exception
            if (line.contains("catch (Exception") || line.contains("catch (Throwable")) {
                comments.add(new ReviewComment(lineNumber,
                        "Catching overly broad exception type",
                        Severity.INFO, ReviewCategory.BEST_PRACTICES,
                        "Catch specific exception types to handle different error conditions appropriately"));
            }

            // == for String comparison
            if (line.matches(".*\".*\"\\s*==|==\\s*\".*\".*") ||
                    line.matches(".*\\.equals\\(.*\\).*") && line.contains("==")) {
                comments.add(new ReviewComment(lineNumber,
                        "Possible String comparison using == instead of .equals()",
                        Severity.ERROR, ReviewCategory.BEST_PRACTICES,
                        "Use String.equals() or Objects.equals() for value-based String comparison"));
            }

            // Magic numbers
            if (line.matches(".*[^\\w](\\d{2,})[^\\d].*") && !line.startsWith("//") &&
                    !line.contains("final") && !line.startsWith("*")) {
                comments.add(new ReviewComment(lineNumber,
                        "Magic number detected — unexplained numeric literal",
                        Severity.INFO, ReviewCategory.BEST_PRACTICES,
                        "Extract magic numbers into named constants to improve readability and maintainability"));
            }

            // Mutable public fields
            if (line.matches("\\s*public\\s+(?!final|static\\s+final)\\w+\\s+\\w+\\s*;.*")) {
                comments.add(new ReviewComment(lineNumber,
                        "Public mutable field detected",
                        Severity.WARNING, ReviewCategory.BEST_PRACTICES,
                        "Make fields private and provide getter/setter methods to maintain encapsulation"));
            }
        }

        return comments;
    }

    private String toUpperSnakeCase(String name) {
        return name.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
    }
}
