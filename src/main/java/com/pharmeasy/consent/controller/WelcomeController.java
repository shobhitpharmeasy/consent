package com.pharmeasy.consent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/")
@Tag(name = "Welcome", description = "Greeting API")
public class WelcomeController {

    @GetMapping("/greeting")
    @PreAuthorize("hasRole('USER') and !hasRole('ADMIN')")
    @Operation(summary = "Greeting API")
    @Cacheable(value = "greeting", key = "#authentication.name")
    public String greeting(final Authentication authentication) {
        final String userName = authentication.getName();
        log.info("Greeting accessed by user: {}", userName);
        return "Spring Security Basic Authentication Consent Management - Welcome " + userName;
    }
}
