package com.pharmeasy.consent.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HashUtils {

    private HashUtils() {
        // Private constructor to prevent instantiation
    }

    public static String hash(String input) {

        PasswordEncoder encoder = passwordEncoder();
        return encoder.encode(input);
    }

    public static PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
