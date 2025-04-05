package com.pharmeast.consent.utils;


public class RandomUtils {

    private static final String CHARACTERS
        = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    private static final java.security.SecureRandom random
        = new java.security.SecureRandom();

    private RandomUtils() {
        // Private constructor to prevent instantiation
    }

    public static String generateRandomPassword() {
        int uid = generateUID();
        String randomChars = generateRandomString(4); // Get 4 random chars
        return randomChars + uid;
    }

    public static int generateUID() {
        return 1000 + random.nextInt(9000); // Ensures 4-digit UID
    }

    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
