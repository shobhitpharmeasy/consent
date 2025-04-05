package com.pharmeast.consent.utils;


public class HashUtils {
    private HashUtils() {

    }

    public static String hash(String input) {
        org.springframework.security.crypto.password.PasswordEncoder encoder
            = passwordEncoder();
        return encoder.encode(input);
    }

    public static org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}
