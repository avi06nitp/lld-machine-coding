package strategy;

import java.security.SecureRandom;

/**
 * Generates a random Base62 short code (alphanumeric, URL-safe).
 * This is the most common approach used by Bit.ly and similar services.
 */
public class Base62Strategy implements CodeGenerationStrategy {
    private static final String BASE62_CHARS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final SecureRandom random = new SecureRandom();

    @Override
    public String generateCode(String originalUrl, int codeLength) {
        StringBuilder sb = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            sb.append(BASE62_CHARS.charAt(random.nextInt(BASE62_CHARS.length())));
        }
        return sb.toString();
    }
}
