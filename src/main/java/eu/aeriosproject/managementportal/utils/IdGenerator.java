package eu.aeriosproject.managementportal.utils;

import java.security.SecureRandom;

public class IdGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static String generateRandomHexId(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        byte[] randomBytes = new byte[length / 2 + length % 2];
        secureRandom.nextBytes(randomBytes);

        char[] hexChars = new char[length];
        for (int i = 0; i < length / 2; i++) {
            int v = randomBytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }

        // If length is odd, fill the last character
        if (length % 2 != 0) {
            int v = randomBytes[randomBytes.length - 1] & 0xFF;
            hexChars[length - 1] = HEX_ARRAY[v >>> 4];
        }

        return new String(hexChars);
    }
}
