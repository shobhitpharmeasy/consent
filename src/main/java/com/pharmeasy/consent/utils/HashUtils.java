package com.pharmeasy.consent.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class HashUtils {

    private HashUtils() {
        // Private constructor to prevent instantiation
        log.debug("HashUtils class loaded - constructor is private");
    }

    public static String hash(String input) {
        log.debug("Hashing input string");
        PasswordEncoder encoder = passwordEncoder();
        String hashed = encoder.encode(input);
        log.debug("Hashed string generated");
        return hashed;
    }

    public static PasswordEncoder passwordEncoder() {
        log.debug("Creating instance of BCryptPasswordEncoder");

        return new BCryptPasswordEncoder(BCryptVersion.$2B, 12);
    }


}
