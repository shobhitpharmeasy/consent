package com.pharmeasy.consent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import static com.pharmeasy.consent.utils.Constants.regexEmail;

@Data
@Builder
public class LoginRequestDto {

    @NotBlank(message = "Email is mandatory")
    @Pattern(
        regexp = regexEmail, message = "Email must be a valid pharmeasy.in address"
    )
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    private String password;

    @NotBlank(message = "Token is mandatory")
    private String token;
}
