package com.pharmeasy.consent.dto;

import com.pharmeasy.consent.entity.ConsentType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import static com.pharmeasy.consent.utils.Constants.REGEX_EMAIL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Pattern(regexp = REGEX_EMAIL, message = "Email must be a valid pharmeasy.in address")
    private String email;

    @NotBlank(message = "First name is mandatory")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]*$", message = "Middle name must contain only letters")
    private String middleName;

    @NotBlank(message = "Last name is mandatory")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    private String lastName;

    private Map<ConsentType, Boolean> dataConsents;

    private List<UserConsentLogDto> consentLogs;
}
