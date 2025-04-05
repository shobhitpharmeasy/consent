package com.pharmeasy.consent.controller;

import com.pharmeasy.consent.dto.ServiceDto;
import com.pharmeasy.consent.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "Service", description = "APIs for managing services, ownership, and access control")
public class ServiceController {

    private final ServiceService serviceService;

    // ─────────────────────────────────────
    // Service CRUD
    // ─────────────────────────────────────

    @PostMapping
    @Operation(summary = "Create a new service")
    public ResponseEntity<ServiceDto> createService(@RequestBody ServiceDto dto) {
        return ResponseEntity.ok(serviceService.createService(dto));
    }

    @GetMapping
    @Operation(summary = "Get all service names")
    public ResponseEntity<List<String>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/{serviceName}")
    @Operation(summary = "Get service details by name")
    public ResponseEntity<ServiceDto> getServiceByName(@PathVariable String serviceName) {
        return ResponseEntity.ok(serviceService.getServiceByName(serviceName));
    }

    @PutMapping
    @Operation(summary = "Update an existing service")
    public ResponseEntity<ServiceDto> updateService(@RequestBody ServiceDto dto) {
        return ResponseEntity.ok(serviceService.updateService(dto));
    }

    @DeleteMapping("/{serviceName}")
    @Operation(summary = "Delete a service by name")
    public ResponseEntity<String> deleteService(@PathVariable String serviceName) {
        return ResponseEntity.ok(serviceService.deleteService(serviceName));
    }

    @GetMapping("/{serviceName}/owner")
    @Operation(summary = "Get the owner of a service")
    public ResponseEntity<String> whoIsOwnerToService(@PathVariable String serviceName) {
        return ResponseEntity.ok(serviceService.whoIsOwnerToService(serviceName));
    }

    // ─────────────────────────────────────
    // Requester Operations
    // ─────────────────────────────────────

    @GetMapping("/requester/{employeeEmail}/requested")
    @Operation(summary = "Get list of services requested by an employee")
    public ResponseEntity<List<String>> whatAreMyRequestedServices(@PathVariable String employeeEmail) {
        return ResponseEntity.ok(serviceService.whatAreMyRequestedServices(employeeEmail));
    }

    @GetMapping("/requester/{employeeEmail}/accessible")
    @Operation(summary = "Get list of services accessible by an employee")
    public ResponseEntity<List<String>> whatAreMyAccessibleServices(@PathVariable String employeeEmail) {
        return ResponseEntity.ok(serviceService.whatAreMyAccessibleServices(employeeEmail));
    }

    @PostMapping("/{serviceName}/request/{employeeEmail}")
    @Operation(summary = "Request access to a service")
    public ResponseEntity<Boolean> requestServiceAccess(
        @PathVariable String serviceName,
        @PathVariable String employeeEmail
    ) {
        return ResponseEntity.ok(serviceService.requestServiceAccess(serviceName, employeeEmail));
    }

    @DeleteMapping("/{serviceName}/request/{employeeEmail}")
    @Operation(summary = "Remove access request to a service")
    public ResponseEntity<Boolean> removeServiceAccess(
        @PathVariable String serviceName,
        @PathVariable String employeeEmail
    ) {
        return ResponseEntity.ok(serviceService.removeServiceAccess(serviceName, employeeEmail));
    }

    @GetMapping("/{serviceName}/access/{employeeEmail}")
    @Operation(summary = "Check if service access is granted to an employee")
    public ResponseEntity<Boolean> isServiceAccessGranted(
        @PathVariable String serviceName,
        @PathVariable String employeeEmail
    ) {
        return ResponseEntity.ok(serviceService.isServiceAccessGranted(serviceName, employeeEmail));
    }

    // ─────────────────────────────────────
    // Owner Operations
    // ─────────────────────────────────────

    @GetMapping("/owner/{employeeEmail}")
    @Operation(summary = "Get services owned by an employee")
    public ResponseEntity<List<String>> whatAreMyServices(@PathVariable String employeeEmail) {
        return ResponseEntity.ok(serviceService.whatAreMyServices(employeeEmail));
    }

    @GetMapping("/{serviceName}/owner/check/{employeeEmail}")
    @Operation(summary = "Check if an employee owns a specific service")
    public ResponseEntity<Boolean> isServiceOwnedByMe(
        @PathVariable String serviceName,
        @PathVariable String employeeEmail
    ) {
        return ResponseEntity.ok(serviceService.isServiceOwnedByMe(serviceName, employeeEmail));
    }

    @PostMapping("/{serviceName}/transfer/{newOwnerEmail}")
    @Operation(summary = "Transfer service ownership to a new owner")
    public ResponseEntity<String> transferServiceOwnership(
        @PathVariable String serviceName,
        @PathVariable String newOwnerEmail
    ) {
        return ResponseEntity.ok(serviceService.transferServiceOwnership(serviceName, newOwnerEmail));
    }

    @PostMapping("/{serviceName}/grant/{employeeEmail}")
    @Operation(summary = "Grant service access to an employee")
    public ResponseEntity<Boolean> addServiceAccess(
        @PathVariable String serviceName,
        @PathVariable String employeeEmail
    ) {
        return ResponseEntity.ok(serviceService.addServiceAccess(serviceName, employeeEmail));
    }

    @GetMapping("/{serviceName}/requests/{ownerEmail}")
    @Operation(summary = "Get list of employees who requested access to a service")
    public ResponseEntity<List<String>> whoHasRequestedMyService(
        @PathVariable String serviceName,
        @PathVariable String ownerEmail
    ) {
        return ResponseEntity.ok(serviceService.whoHasRequestedMyService(serviceName, ownerEmail));
    }

    @GetMapping("/{serviceName}/accessors/{ownerEmail}")
    @Operation(summary = "Get list of employees who have access to a service")
    public ResponseEntity<List<String>> whoHasAccessToMyService(
        @PathVariable String serviceName,
        @PathVariable String ownerEmail
    ) {
        return ResponseEntity.ok(serviceService.whoHasAccessToMyService(serviceName, ownerEmail));
    }

    @PostMapping("/{serviceName}/grant-access/{employeeEmail}")
    @Operation(summary = "Grant service access explicitly (owner only)")
    public ResponseEntity<Boolean> grantServiceAccess(
        @PathVariable String serviceName,
        @PathVariable String employeeEmail
    ) {
        return ResponseEntity.ok(serviceService.grantServiceAccess(serviceName, employeeEmail));
    }

    @PostMapping("/{serviceName}/revoke-access/{employeeEmail}")
    @Operation(summary = "Revoke service access from an employee (owner only)")
    public ResponseEntity<Boolean> revokeServiceAccess(
        @PathVariable String serviceName,
        @PathVariable String employeeEmail
    ) {
        return ResponseEntity.ok(serviceService.revokeServiceAccess(serviceName, employeeEmail));
    }
}
