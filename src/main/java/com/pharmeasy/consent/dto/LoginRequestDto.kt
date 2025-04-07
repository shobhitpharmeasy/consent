package com.pharmeasy.consent.dto

import com.pharmeasy.consent.utils.Constants
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import lombok.Builder
import lombok.Data

@Data
@Builder
class LoginRequestDto {
    private val email: @NotBlank(message = "Email is mandatory") @Pattern(
        regexp = Constants.regexEmail,
        message = "Email must be a valid pharmeasy.in address"
    ) String? = null

    private val password: @NotBlank(message = "Password is mandatory") @Size(
        min = 8,
        max = 64,
        message = "Password must be between 8 and 64 characters"
    ) String? = null

    private val token: @NotBlank(message = "Token is mandatory") String? = null
}
