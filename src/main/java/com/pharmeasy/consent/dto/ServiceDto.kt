package com.pharmeasy.consent.dto

import com.pharmeasy.consent.entity.Service.AccessStatus
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

/**
 * Data Transfer Object for [com.pharmeasy.consent.entity.Service]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ServiceDto {
    private var name: @NotBlank(message = "Service name is mandatory") @Pattern(
        regexp = "^[a-zA-Z0-9\\s]+$",
        message = "Service name must contain only letters, numbers, and spaces"
    ) String? = null

    private var description: @NotBlank(message = "Service description is mandatory") @Pattern(
        regexp = "^[a-zA-Z0-9\\s.,!?]+$",
        message = "Service description contains invalid characters"
    ) String? = null

    private var status: @NotNull(message = "Service status is mandatory") Boolean? = null

    private var createdByEmail: @NotBlank(message = "Creator's email is mandatory") @Pattern(
        regexp = "^[^@]+@pharmeasy\\.in$",
        message = "Creator's email must be a valid pharmeasy.in address"
    ) String? = null

    // Optional: Add validation only if the map is required at creation/update
    // Optional: Add a custom validator for map key/value validation
    private var employeeAccess: @NotNull(message = "Employee access map is mandatory") MutableMap<String, AccessStatus>? =
        null //    private Map<String, AccessStatus> employeeAccess; // For internal use, not exposed to the client
}
