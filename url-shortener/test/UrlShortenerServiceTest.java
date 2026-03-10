package test;

import exception.CustomAliasAlreadyExistsException;
import exception.UrlExpiredException;
import exception.UrlNotFoundException;
import factory.CodeGeneratorFactory;
import enums.EncodingType;
import model.UrlStats;
import service.UrlShortenerService;
import strategy.CodeGenerationStrategy;

/**
 * Manual unit tests for UrlShortenerService.
 * Run: javac -cp . test/UrlShortenerServiceTest.java && java -cp . test.UrlShortenerServiceTest
 */
public class UrlShortenerServiceTest {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) throws InterruptedException {
        UrlShortenerServiceTest tests = new UrlShortenerServiceTest();

        tests.testShortenAndExpand();
        tests.testCustomAlias();
        tests.testDuplicateCustomAlias();
        tests.testClickCountIncrement();
        tests.testUrlExpiry();
        tests.testDeleteUrl();
        tests.testInvalidUrl();
        tests.testHashStrategy();

        System.out.printf("\n=== Results: %d passed, %d failed ===%n", passed, failed);
        if (failed > 0) System.exit(1);
    }

    private UrlShortenerService createService(EncodingType type) {
        CodeGenerationStrategy strategy = CodeGeneratorFactory.create(type);
        return new UrlShortenerService(strategy, 10);
    }

    private void testShortenAndExpand() {
        UrlShortenerService service = createService(EncodingType.BASE62);
        String shortCode = service.shortenUrl("https://www.example.com/some/long/path");
        String original = service.getOriginalUrl(shortCode);
        assertEqual("testShortenAndExpand", "https://www.example.com/some/long/path", original);
    }

    private void testCustomAlias() {
        UrlShortenerService service = createService(EncodingType.BASE62);
        String shortCode = service.shortenUrl("https://www.google.com", "goog", -1);
        assertEqual("testCustomAlias - shortCode", "goog", shortCode);
        assertEqual("testCustomAlias - expand", "https://www.google.com", service.getOriginalUrl("goog"));
    }

    private void testDuplicateCustomAlias() {
        UrlShortenerService service = createService(EncodingType.BASE62);
        service.shortenUrl("https://www.google.com", "goog", -1);
        try {
            service.shortenUrl("https://www.bing.com", "goog", -1);
            fail("testDuplicateCustomAlias", "Expected CustomAliasAlreadyExistsException");
        } catch (CustomAliasAlreadyExistsException e) {
            pass("testDuplicateCustomAlias");
        }
    }

    private void testClickCountIncrement() {
        UrlShortenerService service = createService(EncodingType.BASE62);
        String code = service.shortenUrl("https://www.example.com");
        service.getOriginalUrl(code);
        service.getOriginalUrl(code);
        UrlStats stats = service.getStats(code);
        assertEqual("testClickCountIncrement", 2L, stats.getClickCount());
    }

    private void testUrlExpiry() throws InterruptedException {
        UrlShortenerService service = createService(EncodingType.BASE62);
        // Expire after 100ms
        String code = service.shortenUrl("https://www.example.com", null, 100);
        Thread.sleep(200);
        try {
            service.getOriginalUrl(code);
            fail("testUrlExpiry", "Expected UrlExpiredException");
        } catch (UrlExpiredException e) {
            pass("testUrlExpiry");
        }
    }

    private void testDeleteUrl() {
        UrlShortenerService service = createService(EncodingType.BASE62);
        String code = service.shortenUrl("https://www.example.com");
        service.deleteUrl(code);
        try {
            service.getOriginalUrl(code);
            fail("testDeleteUrl", "Expected UrlNotFoundException after deletion");
        } catch (UrlNotFoundException e) {
            pass("testDeleteUrl");
        }
    }

    private void testInvalidUrl() {
        UrlShortenerService service = createService(EncodingType.BASE62);
        try {
            service.shortenUrl("not-a-valid-url");
            fail("testInvalidUrl", "Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            pass("testInvalidUrl");
        }
    }

    private void testHashStrategy() {
        UrlShortenerService service = createService(EncodingType.HASH);
        String url = "https://www.deterministic.com";
        String code1 = service.shortenUrl(url);
        // Hash strategy should produce consistent-length codes
        assertEqual("testHashStrategy - code length", 6, code1.length());
        pass("testHashStrategy");
    }

    // --- test helpers ---

    private void assertEqual(String testName, Object expected, Object actual) {
        if (expected.equals(actual)) {
            pass(testName);
        } else {
            fail(testName, "Expected: " + expected + ", Got: " + actual);
        }
    }

    private void pass(String testName) {
        System.out.println("[PASS] " + testName);
        passed++;
    }

    private void fail(String testName, String reason) {
        System.out.println("[FAIL] " + testName + " - " + reason);
        failed++;
    }
}
