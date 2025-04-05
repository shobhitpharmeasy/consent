package com.pharmeast.consent.dto;

/**
 * Data Transfer Object for {@link com.pharmeast.consent.entity.Service}
 */
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class ServiceDto {

    @jakarta.validation.constraints.NotBlank(message = "Service name is mandatory")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[a-zA-Z0-9\\s]+$",
        message = "Service name must contain only letters, numbers, and spaces"
    )
    private String name;

    @jakarta.validation.constraints.NotBlank(message = "Service description is mandatory")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[a-zA-Z0-9\\s.,!?]+$",
        message = "Service description contains invalid characters"
    )
    private String description;

    @jakarta.validation.constraints.NotNull(message = "Service status is mandatory")
    private Boolean status;

    @jakarta.validation.constraints.NotBlank(message = "Creator's email is mandatory")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[^@]+@pharmeasy\\.in$",
        message = "Creator's email must be a valid pharmeasy.in address"
    )
    private String createdByEmail;
}
