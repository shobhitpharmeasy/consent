package com.pharmeasy.consent.controller;

import com.pharmeasy.consent.dto.EmployeeDto;
import com.pharmeasy.consent.dto.LoginRequestDto;
import com.pharmeasy.consent.service.EmployeeService;
import com.pharmeasy.consent.service.LoginJwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee", description = "Employee management APIs")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LoginJwtService loginService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new employee")
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        log.info("Received request to create employee with email: {}", employeeDto.getEmail());

        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(
            createdEmployee.getEmail()).toUri();

        log.info("Employee created successfully with email: {}", createdEmployee.getEmail());
        return ResponseEntity.created(location).body(createdEmployee);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        log.info("Received request to fetch all employees");

        List<EmployeeDto> employees = employeeService.getAllEmployee();
        log.info("Fetched {} employees", employees.size());

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get employee by email")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable @Email @NotBlank String employeeEmail) {
        log.info("Received request to fetch employee with email: {}", employeeEmail);

        EmployeeDto employee = employeeService.getEmployeeByEmail(employeeEmail);
        log.info("Employee details fetched for email: {}", employeeEmail);

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{employeeEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update employee by email")
    public ResponseEntity<EmployeeDto> updateEmployee(
        @PathVariable @Email @NotBlank String employeeEmail,
        @RequestBody @Valid EmployeeDto employeeDto
    ) {
        log.info("Received request to update employee with email: {}", employeeEmail);

        if (employeeDto.getEmail() != null && !employeeDto.getEmail().equalsIgnoreCase(employeeEmail)) {
            log.warn(
                "Email mismatch in update request: pathEmail={}, bodyEmail={}", employeeEmail, employeeDto.getEmail());
            return ResponseEntity.badRequest().body(null);
        }

        EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeEmail, employeeDto);
        log.info("Employee updated successfully for email: {}", employeeEmail);

        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{employeeEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete employee by email")
    public ResponseEntity<String> deleteEmployee(@PathVariable @Email @NotBlank String employeeEmail) {
        log.info("Received request to delete employee with email: {}", employeeEmail);

        String result = employeeService.deleteEmployee(employeeEmail);
        log.info("Employee deleted successfully with email: {}", employeeEmail);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{employeeEmail}/password")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Update employee password")
    public ResponseEntity<String> updatePassword(
        @PathVariable @Email @NotBlank String employeeEmail,
        @RequestBody @NotNull LoginRequestDto passwordRequest
    ) {
        log.info(
            "Received request to update password for employee: {} with pass {} and token {}", employeeEmail,
            passwordRequest.getPassword(), passwordRequest.getToken()
        );

        if (Boolean.FALSE.equals(
            loginService.isAuthorized(createLoginRequestDto(employeeEmail, passwordRequest.getToken())))) {
            log.warn("Unauthorized password update attempt for employee: {}", employeeEmail);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
        }

        String newPassword = passwordRequest.getPassword();
        if (newPassword == null || newPassword.isBlank()) {
            log.warn("Password update failed for employee {}: new password is blank", employeeEmail);
            return ResponseEntity.badRequest().body("New password must not be blank");
        }

        String response = employeeService.updatePassword(employeeEmail, newPassword);
        log.info("Password updated successfully for employee: {}", employeeEmail);

        return ResponseEntity.ok(response);
    }

    private LoginRequestDto createLoginRequestDto(String email, String token) {
        return LoginRequestDto.builder().email(email).token(token).build();
    }
}
