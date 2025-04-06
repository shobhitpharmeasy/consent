package com.pharmeasy.consent.dto;

import com.pharmeasy.consent.entity.Employee;
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
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.pharmeasy.consent.utils.Constants.regexEmail;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class EmployeeDto {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Pattern(
        regexp = regexEmail, message = "Email must be from pharmeasy.in domain"
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

    private List<String> ownedServices;
    
    private List<String> requestedServices;
    private List<String> accessibleServices;
}
