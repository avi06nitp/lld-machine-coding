package strategy;

/**
 * Strategy interface for generating short codes from original URLs.
 * Implementations can use different algorithms (Base62, Hash, etc.)
 */
public interface CodeGenerationStrategy {
    /**
     * Generates a short code for the given original URL.
     *
     * @param originalUrl the URL to shorten
     * @param codeLength  the desired length of the short code
     * @return a short code string
     */
    String generateCode(String originalUrl, int codeLength);
}
