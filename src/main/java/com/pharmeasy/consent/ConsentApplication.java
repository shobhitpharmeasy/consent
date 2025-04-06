package com.pharmeasy.consent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ConsentApplication {

    public static void main(String[] args) {
        log.info("Starting Consent Management Application...");
        SpringApplication.run(ConsentApplication.class, args);
        log.info("Consent Management Application started successfully.");
    }
}
