import controller.UrlController;
import enums.EncodingType;
import factory.CodeGeneratorFactory;
import service.UrlShortenerService;
import strategy.CodeGenerationStrategy;

import java.util.Scanner;

/**
 * Entry point for the URL Shortener application.
 *
 * Design Patterns used:
 *  - Strategy  : CodeGenerationStrategy (Base62 / Hash)
 *  - Factory   : CodeGeneratorFactory creates the right strategy
 *  - Singleton : UrlShortenerService acts as single source of truth
 *  - Cache     : LRU UrlCache reduces store lookups
 */
public class UrlShortenerApp {
    public static void main(String[] args) {
        // Default: Base62 random encoding, LRU cache size 100
        EncodingType encodingType = EncodingType.BASE62;
        int cacheCapacity = 100;

        CodeGenerationStrategy strategy = CodeGeneratorFactory.create(encodingType);
        UrlShortenerService service = new UrlShortenerService(strategy, cacheCapacity);

        Scanner scanner = new Scanner(System.in);
        UrlController controller = new UrlController(service, scanner);
        controller.run();

        scanner.close();
    }
}
