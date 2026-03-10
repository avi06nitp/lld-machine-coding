package strategy;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Generates a deterministic short code by hashing the original URL with MD5.
 * Same URL will always produce the same short code (idempotent shortening).
 */
public class HashStrategy implements CodeGenerationStrategy {
    private static final String BASE62_CHARS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public String generateCode(String originalUrl, int codeLength) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hashBytes = digest.digest(originalUrl.getBytes(StandardCharsets.UTF_8));

            // Convert hash bytes to a Base62 short code
            StringBuilder sb = new StringBuilder(codeLength);
            for (int i = 0; i < codeLength; i++) {
                int index = (hashBytes[i % hashBytes.length] & 0xFF) % BASE62_CHARS.length();
                sb.append(BASE62_CHARS.charAt(index));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
}
