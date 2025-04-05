package com.pharmeasy.consent.controller;

import com.pharmeasy.consent.dto.EmployeeDto;
import com.pharmeasy.consent.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee", description = "Employee management APIs")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new employee")
    public ResponseEntity<EmployeeDto> createEmployee(
        @Valid @RequestBody EmployeeDto employeeDto
    ) {

        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(
            createdEmployee.getEmail()).toUri();

        return ResponseEntity.created(location).body(createdEmployee);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {

        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @GetMapping("/{employeeEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get employee by email")
    public ResponseEntity<EmployeeDto> getEmployeeById(
        @PathVariable @Email @NotBlank String employeeEmail
    ) {

        return ResponseEntity.ok(employeeService.getEmployeeByEmail(employeeEmail));
    }

    @PutMapping("/{employeeEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update employee by email")
    public ResponseEntity<EmployeeDto> updateEmployee(
        @PathVariable @Email @NotBlank String employeeEmail,
        @RequestBody @Valid EmployeeDto employeeDto
    ) {

        if (employeeDto.getEmail() != null && !employeeDto.getEmail().equalsIgnoreCase(employeeEmail)) {
            return ResponseEntity.badRequest().body(null); // optionally return a message or throw a custom exception
        }

        EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeEmail, employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{employeeEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete employee by email")
    public ResponseEntity<String> deleteEmployee(
        @PathVariable @Email @NotBlank String employeeEmail
    ) {

        return ResponseEntity.ok(employeeService.deleteEmployee(employeeEmail));
    }

    @PutMapping("/{employeeEmail}/password")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Update employee password")
    public ResponseEntity<String> updatePassword(
        @PathVariable @Email @NotBlank String employeeEmail, @RequestBody @NotNull Map<String, String> passwordRequest,
        @RequestHeader("Authorization") @NotBlank String token
    ) {

        if (Boolean.FALSE.equals(employeeService.isAuthorized(employeeEmail, token))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
        }

        String newPassword = passwordRequest.get("newPassword");
        if (newPassword == null || newPassword.isBlank()) {
            return ResponseEntity.badRequest().body("New password must not be blank");
        }

        String response = employeeService.updatePassword(employeeEmail, newPassword);
        return ResponseEntity.ok(response);
    }
}
