package com.pharmeast.consent.dto;


@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class EmployeeDto {

    @jakarta.validation.constraints.NotBlank(message = "Email is mandatory")
    @jakarta.validation.constraints.Email(message = "Invalid email format")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[^@]+@pharmeasy\\.in$",
        message = "Email must be from pharmeasy.in domain"
    )
    private String email;

    @jakarta.validation.constraints.NotBlank(message = "First name is mandatory")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[a-zA-Z]+$", message = "First name must contain only letters"
    )
    private String firstName;

    @jakarta.validation.constraints.Pattern(
        regexp = "^[a-zA-Z]*$", message = "Middle name must contain only letters"
    )
    private String middleName;

    @jakarta.validation.constraints.NotBlank(message = "Last name is mandatory")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters"
    )
    private String lastName;

    @jakarta.validation.constraints.NotBlank(message = "Password hash is mandatory")
    @jakarta.validation.constraints.Size(
        min = 512, max = 512, message = "Password hash must be exactly 512 characters"
    )
    private String passwordHash;

    @jakarta.validation.constraints.NotBlank(message = "Role is mandatory")
    @jakarta.validation.constraints.Pattern(
        regexp = "ADMIN|USER", message = "Role must be either 'ADMIN' or 'USER'"
    )
    private String role;
}
