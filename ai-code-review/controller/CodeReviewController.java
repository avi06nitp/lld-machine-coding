package controller;

import enums.Language;
import exception.CodeReviewException;
import model.CodeSnippet;
import model.ReviewReport;
import service.CodeReviewService;
import service.ReportService;

import java.util.Scanner;

/**
 * Controller that manages user interaction for the code review system.
 * Handles input collection, delegates to services, and coordinates output.
 */
public class CodeReviewController {

    private final CodeReviewService reviewService;
    private final ReportService reportService;
    private final Scanner scanner;

    public CodeReviewController(CodeReviewService reviewService, ReportService reportService) {
        this.reviewService = reviewService;
        this.reportService = reportService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the interactive code review session.
     */
    public void start() {
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║           AI-BASED CODE REVIEW SYSTEM                               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
        System.out.println("Analyzes code for naming conventions, complexity, security, and best practices.");
        System.out.println();

        boolean continueReviewing = true;

        while (continueReviewing) {
            try {
                CodeSnippet snippet = collectCodeInput();
                ReviewReport report = reviewService.review(snippet);
                reportService.printReport(report);
            } catch (CodeReviewException e) {
                System.out.println("[Error] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[Unexpected error] " + e.getMessage());
            }

            continueReviewing = askToReviewAnother();
        }

        System.out.println();
        System.out.println("Thank you for using the AI Code Review System. Goodbye!");
        scanner.close();
    }

    /**
     * Collects code snippet input interactively from the user.
     */
    private CodeSnippet collectCodeInput() {
        System.out.print("Enter file name (e.g., MyClass.java): ");
        String fileName = scanner.nextLine().trim();

        if (fileName.isBlank()) {
            throw new CodeReviewException("File name cannot be empty");
        }

        Language language = detectLanguage(fileName);
        System.out.println("Detected language: " + language.name());
        System.out.println();
        System.out.println("Paste your code below. Enter a blank line followed by 'END' to finish:");
        System.out.println();

        StringBuilder codeBuilder = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("END")) {
            codeBuilder.append(line).append("\n");
        }

        String code = codeBuilder.toString();
        if (code.isBlank()) {
            throw new CodeReviewException("Code snippet cannot be empty");
        }

        return new CodeSnippet(fileName, code, language);
    }

    /**
     * Detects the language from the file name extension.
     */
    private Language detectLanguage(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == fileName.length() - 1) {
            return Language.GENERAL;
        }
        String extension = fileName.substring(dotIndex + 1);
        return Language.fromExtension(extension);
    }

    /**
     * Prompts the user to review another snippet.
     */
    private boolean askToReviewAnother() {
        System.out.println();
        System.out.print("Would you like to review another file? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }
}
