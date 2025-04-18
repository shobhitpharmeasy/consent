package com.pharmeasy.consent.dto;

import com.pharmeasy.consent.entity.Employee.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.pharmeasy.consent.utils.Constants.regexEmail;

@Slf4j
public record EmployeeDto(
    @NotBlank(message = "Email is mandatory") @Email(message = "Invalid email" +
                                                               " format") @Pattern(
        regexp = regexEmail, message = "Email must be from pharmeasy.in domain"
    ) String email,

    @NotBlank(message = "First name is mandatory") @Pattern(
        regexp = "^[a-zA-Z]+$", message = "First name must contain only letters"
    ) String firstName,

    @Pattern(
        regexp = "^[a-zA-Z]*$",
        message = "Middle name must contain only letters"
    ) String middleName,

    @NotBlank(message = "Last name is mandatory") @Pattern(
        regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters"
    ) String lastName,

    @NotNull(message = "Role is mandatory") Role role,

    List<String> ownedServices,
    List<String> requestedServices,
    List<String> accessibleServices
)
{
}
