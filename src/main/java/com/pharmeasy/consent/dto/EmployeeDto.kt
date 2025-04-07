package com.pharmeasy.consent.dto

import com.pharmeasy.consent.entity.Employee
import com.pharmeasy.consent.utils.Constants
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import lombok.*
import lombok.extern.slf4j.Slf4j

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
class EmployeeDto {
    private var email: @NotBlank(message = "Email is mandatory") @Email(message = "Invalid email format") @Pattern(
        regexp = Constants.regexEmail,
        message = "Email must be from pharmeasy.in domain"
    ) String? = null

    private var firstName: @NotBlank(message = "First name is mandatory") @Pattern(
        regexp = "^[a-zA-Z]+$",
        message = "First name must contain only letters"
    ) String? = null

    private var middleName: @Pattern(
        regexp = "^[a-zA-Z]*$",
        message = "Middle name must contain only letters"
    ) String? = null

    private var lastName: @NotBlank(message = "Last name is mandatory") @Pattern(
        regexp = "^[a-zA-Z]+$",
        message = "Last name must contain only letters"
    ) String? = null

    @Enumerated(EnumType.STRING)
    private var role: @NotNull(message = "Role is mandatory") Employee.Role? = null

    private var ownedServices: List<String>? = null

    private var requestedServices: List<String>? = null
    private var accessibleServices: List<String>? = null
}
