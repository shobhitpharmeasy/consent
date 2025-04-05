package com.pharmeast.consent.entity;

@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "employees")
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class Employee {

    @jakarta.persistence.Id
    @jakarta.persistence.Column(name = "email", nullable = false, unique = true)
    @jakarta.validation.constraints.NotBlank(message = "Email is mandatory")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[^@]+@pharmeasy\\.in$",
        message = "Email must be a valid pharmeasy.in address"
    )
    private String email;

    @jakarta.persistence.Column(name = "first_name", nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "First name is mandatory")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[a-zA-Z]+$", message = "First name must contain only letters"
    )
    private String firstName;

    @jakarta.persistence.Column(name = "middle_name")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[a-zA-Z]*$", message = "Middle name must contain only letters"
    )
    private String middleName;

    @jakarta.persistence.Column(name = "last_name", nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "Last name is mandatory")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters"
    )
    private String lastName;

    @jakarta.persistence.Column(name = "password_hash", nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "Password hash is mandatory")
    @jakarta.validation.constraints.Size(
        min = 512, max = 512, message = "Password hash must be exactly 512 characters"
    )
    private String passwordHash;

    @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
    @jakarta.persistence.Column(name = "role", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Role is mandatory")
    private Role role;

    @jakarta.persistence.OneToMany(
        mappedBy = "createdBy", cascade = jakarta.persistence.CascadeType.ALL,
        orphanRemoval = true
    )
    private java.util.List< Service > ownedServices = new java.util.ArrayList<>();

    public enum Role {
        ADMIN, USER
    }
}
