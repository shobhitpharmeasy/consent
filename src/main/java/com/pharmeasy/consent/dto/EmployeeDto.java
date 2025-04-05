package com.pharmeasy.consent.dto;

import com.pharmeasy.consent.entity.Employee;
import com.pharmeasy.consent.entity.Service;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Pattern(
        regexp = "^[^@]+@pharmeasy\\.in$",
        message = "Email must be from pharmeasy.in domain"
    )
    private String email;

    @NotBlank(message = "First name is mandatory")
    @Pattern(
        regexp = "^[a-zA-Z]+$", message = "First name must contain only letters"
    )
    private String firstName;

    @Pattern(
        regexp = "^[a-zA-Z]*$", message = "Middle name must contain only letters"
    )
    private String middleName;

    @NotBlank(message = "Last name is mandatory")
    @Pattern(
        regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters"
    )
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is mandatory")
    private Employee.Role role;

    // ✅ No validation — for serialization only
    private List< Service > ownedServices;
    private List< Service > requestedServices;
    private List< Service > accessibleServices;
}
