package com.pharmeasy.consent.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pharmeasy.consent.utils.Constants.REGEX_EMAIL;

@Entity
@Table(name = "users")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE email = ?")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is mandatory")
    @Pattern(
        regexp = REGEX_EMAIL, message = "Email must be a valid pharmeasy.in address"
    )
    private String email;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name is mandatory")
    @Pattern(
        regexp = "^[a-zA-Z]+$", message = "First name must contain only letters"
    )
    private String firstName;

    @Column(name = "middle_name")
    @Pattern(
        regexp = "^[a-zA-Z]*$", message = "Middle name must contain only letters"
    )
    private String middleName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name is mandatory")
    @Pattern(
        regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters"
    )
    private String lastName;

    @ElementCollection
    @CollectionTable(name = "user_consents", joinColumns = @JoinColumn(name = "user_email"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "consent_type")
    @Column(name = "is_consented", nullable = false)
    @Builder.Default
    private Map<ConsentType, Boolean> dataConsents = new HashMap<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<UserConsentLog> consentLogs = new ArrayList<>();

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

}
