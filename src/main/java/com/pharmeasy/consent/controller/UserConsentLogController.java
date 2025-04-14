package com.pharmeasy.consent.controller;

import com.pharmeasy.consent.entity.UserConsentLog;
import com.pharmeasy.consent.service.UserConsentLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/consents")
@RequiredArgsConstructor
@Tag(name = "UserConsentLog", description = "Consent log management APIs")
public class UserConsentLogController {

    private final UserConsentLogService userConsentLogService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all user consent logs")
    public ResponseEntity<List<UserConsentLog>> getAll() {
        log.info("Fetching all user consent logs");
        return ResponseEntity.ok(userConsentLogService.findAll());
    }

    @GetMapping("/user/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get consent logs by user email")
    public ResponseEntity<List<UserConsentLog>> getByUserEmail(@PathVariable @NotBlank String email) {
        log.info("Fetching consent logs for email: {}", email);
        return ResponseEntity.ok(userConsentLogService.findByUser(email));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete consent log by ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting consent log with id: {}", id);
        userConsentLogService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
