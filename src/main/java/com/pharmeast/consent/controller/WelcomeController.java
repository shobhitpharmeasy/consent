package com.pharmeast.consent.controller;

@org.springframework.web.bind.annotation.RestController
@io.swagger.v3.oas.annotations.tags.Tag(
    name = "Welcome Check", description = "Welcome test API"
)
public class WelcomeController {

    @org.springframework.web.bind.annotation.GetMapping("/greeting")
    @org.springframework.security.access.prepost.PreAuthorize(
        "hasRole('USER') and hasRole('ADMIN')"
    )
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Greeting API"
    )
    public String greeting(
        org.springframework.security.core.Authentication authentication
    ) {

        String userName = authentication.getName();
        return "Spring Security Basic Authentication Consent Management - Welcome " +
               userName;
    }
}
