package com.pharmeasy.consent.dto;

import com.pharmeasy.consent.entity.Service.AccessStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Map;

public record ServiceDto(
    @NotBlank(message = "Service name is mandatory") @Pattern(
        regexp = "^[a-zA-Z0-9\\s]+$",
        message = "Service name must contain only letters, numbers, and spaces"
    ) String name,

    @NotBlank(message = "Service description is mandatory") @Pattern(
        regexp = "^[a-zA-Z0-9\\s.,!?]+$",
        message = "Service description contains invalid characters"
    ) String description,

    @NotNull(message = "Service status is mandatory") Boolean status,

    @NotBlank(message = "Creator's email is mandatory") @Pattern(
        regexp = "^[^@]+@pharmeasy\\.in$",
        message = "Creator's email must be a valid pharmeasy.in address"
    ) String createdByEmail,

    @NotNull(message = "Employee access map is mandatory") Map<@Pattern(
        regexp = "^[^@]+@pharmeasy\\.in$",
        message = "Each email must be a valid pharmeasy.in address"
    ) String, @NotNull(message = "Access is mandatory") AccessStatus> employeeAccess
)
{
}
