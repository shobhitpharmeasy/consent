package com.pharmeasy.consent.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

@Slf4j
public class RandomUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    private static final SecureRandom random = new SecureRandom();

    private RandomUtils() {
        log.debug("RandomUtils loaded - constructor is private");
    }

    public static String generateRandomPassword() {
        int uid = generateUID();
        String randomChars = generateRandomString(4); // Get 4 random chars
        String password = randomChars + uid;

        log.info("Generated random password with pattern: ****{}", uid);
        return password;
    }

    public static int generateUID() {
        int uid = 1000 + random.nextInt(9000); // Ensures 4-digit UID
        log.debug("Generated UID: {}", uid);
        return uid;
    }

    private static String generateRandomString(final int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            sb.append(ch);
        }
        log.debug("Generated random string of length {}: {}", length, sb);
        return sb.toString();
    }
}
