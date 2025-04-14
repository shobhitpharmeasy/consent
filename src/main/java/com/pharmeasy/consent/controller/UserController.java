package com.pharmeasy.consent.controller;

import com.pharmeasy.consent.dto.UserDto;
import com.pharmeasy.consent.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Creating user: {}", userDto.getEmail());
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Fetching all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by email")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable @Email @NotBlank String userEmail) {
        log.info("Fetching user: {}", userEmail);
        return ResponseEntity.ok(userService.getUserByEmail(userEmail));
    }

    @PutMapping("/{userEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user by email")
    public ResponseEntity<UserDto> updateUser(
        @PathVariable @Email @NotBlank String userEmail,
        @RequestBody @Valid UserDto userDto
    ) {
        log.info("Updating user: {}", userEmail);
        if (userDto.getEmail() != null && !userDto.getEmail().equalsIgnoreCase(userEmail)) {
            log.warn("Email mismatch: path={}, body={}", userEmail, userDto.getEmail());
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(userService.updateUser(userEmail, userDto));
    }

    @DeleteMapping("/{userEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user by email")
    public ResponseEntity<String> deleteUser(@PathVariable @Email @NotBlank String userEmail) {
        log.info("Deleting user: {}", userEmail);
        return ResponseEntity.ok(userService.deleteUser(userEmail));
    }
}
