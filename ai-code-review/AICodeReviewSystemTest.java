import enums.Language;
import enums.Severity;
import model.CodeSnippet;
import model.ReviewReport;
import service.CodeReviewService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Tests for the AI-Based Code Review System.
 * Validates that the review engine correctly detects various code issues.
 */
public class AICodeReviewSystemTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("Running AI Code Review System Tests...");
        System.out.println("─".repeat(60));

        testHardcodedCredentialDetected();
        testPascalCaseViolationDetected();
        testHighParameterCountDetected();
        testPythonBareExceptDetected();
        testPythonEvalDetected();
        testCleanCodePassesWithNoErrors();
        testEmptyCodeThrowsException();
        testJavaUpperSnakeCaseConstantViolation();

        System.out.println("─".repeat(60));
        System.out.printf("Results: %d passed, %d failed%n", passed, failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    static void testHardcodedCredentialDetected() {
        String code = "public class Foo {\n    String password = \"supersecret\";\n}";
        CodeSnippet snippet = new CodeSnippet("Foo.java", code, Language.JAVA);
        ReviewReport report = new CodeReviewService().review(snippet);

        boolean found = report.getComments().stream()
                .anyMatch(c -> c.getSeverity() == Severity.ERROR &&
                        c.getMessage().toLowerCase().contains("credential"));
        assert_("testHardcodedCredentialDetected", found,
                "Expected an ERROR for hardcoded credential");
    }

    static void testPascalCaseViolationDetected() {
        String code = "public class myBadClassName {\n}";
        CodeSnippet snippet = new CodeSnippet("myBadClassName.java", code, Language.JAVA);
        ReviewReport report = new CodeReviewService().review(snippet);

        boolean found = report.getComments().stream()
                .anyMatch(c -> c.getMessage().contains("myBadClassName") &&
                        c.getSeverity() == Severity.ERROR);
        assert_("testPascalCaseViolationDetected", found,
                "Expected an ERROR for non-PascalCase class name");
    }

    static void testHighParameterCountDetected() {
        String code = "public class Foo {\n    public void bar(int a, int b, int c, int d, int e, int f) {}\n}";
        CodeSnippet snippet = new CodeSnippet("Foo.java", code, Language.JAVA);
        ReviewReport report = new CodeReviewService().review(snippet);

        boolean found = report.getComments().stream()
                .anyMatch(c -> c.getMessage().contains("parameters"));
        assert_("testHighParameterCountDetected", found,
                "Expected a warning for too many parameters");
    }

    static void testPythonBareExceptDetected() {
        String code = "def foo():\n    try:\n        pass\n    except:\n        pass\n";
        CodeSnippet snippet = new CodeSnippet("foo.py", code, Language.PYTHON);
        ReviewReport report = new CodeReviewService().review(snippet);

        boolean found = report.getComments().stream()
                .anyMatch(c -> c.getMessage().toLowerCase().contains("bare except"));
        assert_("testPythonBareExceptDetected", found,
                "Expected an ERROR for bare except clause");
    }

    static void testPythonEvalDetected() {
        String code = "def foo(x):\n    return eval(x)\n";
        CodeSnippet snippet = new CodeSnippet("foo.py", code, Language.PYTHON);
        ReviewReport report = new CodeReviewService().review(snippet);

        boolean found = report.getComments().stream()
                .anyMatch(c -> c.getMessage().contains("eval()") &&
                        c.getSeverity() == Severity.ERROR);
        assert_("testPythonEvalDetected", found,
                "Expected an ERROR for eval() usage");
    }

    static void testCleanCodePassesWithNoErrors() {
        String code = "public class Calculator {\n" +
                "    private static final int MAX_VALUE = 1000;\n" +
                "\n" +
                "    public int add(int a, int b) {\n" +
                "        return a + b;\n" +
                "    }\n" +
                "}\n";
        CodeSnippet snippet = new CodeSnippet("Calculator.java", code, Language.JAVA);
        ReviewReport report = new CodeReviewService().review(snippet);

        assert_("testCleanCodePassesWithNoErrors", !report.hasCriticalIssues(),
                "Expected no critical errors for clean code, but found: " +
                        report.getCommentsBySeverity(Severity.ERROR).stream()
                                .map(c -> c.getMessage())
                                .collect(Collectors.joining(", ")));
    }

    static void testEmptyCodeThrowsException() {
        boolean threw = false;
        try {
            new CodeSnippet("Empty.java", null, Language.JAVA);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assert_("testEmptyCodeThrowsException", threw,
                "Expected IllegalArgumentException for null content");
    }

    static void testJavaUpperSnakeCaseConstantViolation() {
        String code = "public class Foo {\n    static final int maxRetries = 3;\n}";
        CodeSnippet snippet = new CodeSnippet("Foo.java", code, Language.JAVA);
        ReviewReport report = new CodeReviewService().review(snippet);

        boolean found = report.getComments().stream()
                .anyMatch(c -> c.getMessage().contains("maxRetries") &&
                        c.getMessage().contains("UPPER_SNAKE_CASE"));
        assert_("testJavaUpperSnakeCaseConstantViolation", found,
                "Expected a WARNING for non-UPPER_SNAKE_CASE constant name");
    }

    static void assert_(String testName, boolean condition, String failMessage) {
        if (condition) {
            System.out.printf("  PASS  %s%n", testName);
            passed++;
        } else {
            System.out.printf("  FAIL  %s — %s%n", testName, failMessage);
            failed++;
        }
    }
}
