package com.pharmeasy.consent.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Data Transfer Object for {@link com.pharmeasy.consent.entity.Service}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDto {

    @NotBlank(message = "Service name is mandatory")
    @Pattern(
        regexp = "^[a-zA-Z0-9\\s]+$", message = "Service name must contain only letters, numbers, and spaces"
    )
    private String name;

    @NotBlank(message = "Service description is mandatory")
    @Pattern(
        regexp = "^[a-zA-Z0-9\\s.,!?]+$", message = "Service description contains invalid characters"
    )
    private String description;

    @NotNull(message = "Service status is mandatory")
    private Boolean status;

    @NotBlank(message = "Creator's email is mandatory")
    @Pattern(
        regexp = "^[^@]+@pharmeasy\\.in$", message = "Creator's email must be a valid pharmeasy.in address"
    )
    private String createdByEmail;

    // Optional: Add validation only if the map is required at creation/update
    @NotNull(message = "Employee access map is mandatory")
// Optional: Add a custom validator for map key/value validation
    private Map<@Pattern(
        regexp = "^[^@]+@pharmeasy\\.in$", message = "Each email must be a valid pharmeasy.in address"
    ) String, @Min(value = 0, message = "Access level must be non-negative") @Max(
        value = 2, message = "Access level must be reasonable"
    ) Integer> employeeAccess;

}
