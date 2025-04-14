package com.pharmeasy.consent.dto;

import com.pharmeasy.consent.entity.ConsentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserConsentLogDto {

    private Long id;

    @NotNull(message = "Consent type is mandatory")
    private ConsentType consentType;

    @NotNull(message = "Consent value is mandatory")
    private Boolean consented;

    @NotNull(message = "Timestamp is mandatory")
    private LocalDateTime timestamp;

    @NotNull(message = "User email is mandatory")
    private String userEmail;
}
