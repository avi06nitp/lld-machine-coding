package controller;

import exception.CustomAliasAlreadyExistsException;
import exception.UrlExpiredException;
import exception.UrlNotFoundException;
import model.UrlStats;
import service.UrlShortenerService;

import java.util.Scanner;

/**
 * CLI controller that drives the URL shortener via console interaction.
 */
public class UrlController {
    private final UrlShortenerService urlShortenerService;
    private final Scanner scanner;

    public UrlController(UrlShortenerService urlShortenerService, Scanner scanner) {
        this.urlShortenerService = urlShortenerService;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("=== URL Shortener (Bit.ly Clone) ===");
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> handleShortenUrl();
                case 2 -> handleExpandUrl();
                case 3 -> handleGetStats();
                case 4 -> handleDeleteUrl();
                case 5 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Shorten URL");
        System.out.println("2. Expand short URL");
        System.out.println("3. Get URL stats");
        System.out.println("4. Delete short URL");
        System.out.println("5. Exit");
    }

    private void handleShortenUrl() {
        System.out.print("Enter original URL: ");
        String originalUrl = scanner.nextLine().trim();

        System.out.print("Custom alias (leave blank for auto): ");
        String alias = scanner.nextLine().trim();
        if (alias.isEmpty()) alias = null;

        System.out.print("Expiry in seconds (0 = no expiry): ");
        long expirySeconds = readLong();
        long expiryMs = expirySeconds > 0 ? expirySeconds * 1000 : -1;

        try {
            String shortCode = urlShortenerService.shortenUrl(originalUrl, alias, expiryMs);
            System.out.println("Short code: " + shortCode);
        } catch (CustomAliasAlreadyExistsException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleExpandUrl() {
        System.out.print("Enter short code: ");
        String shortCode = scanner.nextLine().trim();

        try {
            String originalUrl = urlShortenerService.getOriginalUrl(shortCode);
            System.out.println("Original URL: " + originalUrl);
        } catch (UrlNotFoundException | UrlExpiredException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleGetStats() {
        System.out.print("Enter short code: ");
        String shortCode = scanner.nextLine().trim();

        try {
            UrlStats stats = urlShortenerService.getStats(shortCode);
            System.out.println(stats);
        } catch (UrlNotFoundException | UrlExpiredException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleDeleteUrl() {
        System.out.print("Enter short code to delete: ");
        String shortCode = scanner.nextLine().trim();

        try {
            urlShortenerService.deleteUrl(shortCode);
            System.out.println("Deleted successfully.");
        } catch (UrlNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private long readLong() {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print("> ");
            }
        }
    }
}
