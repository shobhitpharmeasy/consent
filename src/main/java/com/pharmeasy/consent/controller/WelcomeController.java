package com.pharmeasy.consent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Welcome", description = "Greeting API")
public class WelcomeController {

    @GetMapping("/greeting")
    @PreAuthorize("hasRole('USER') and hasRole('ADMIN')")
    @Operation(summary = "Greeting API")
    public String greeting(Authentication authentication) {

        String userName = authentication.getName();
        return "Spring Security Basic Authentication Consent Management - Welcome " + userName;
    }
}
