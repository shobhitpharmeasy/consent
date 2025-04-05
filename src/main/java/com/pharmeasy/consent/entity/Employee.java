package com.pharmeasy.consent.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is mandatory")
    @Pattern(
        regexp = "^[^@]+@pharmeasy\\.in$", message = "Email must be a valid pharmeasy.in address"
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

    @Column(name = "password_hash", nullable = false)
    @NotBlank(message = "Password hash is mandatory")
    @Size(min = 60, max = 60, message = "Password hash must be exactly 512 characters")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @NotNull(message = "Role is mandatory")
    private Role role;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Service> ownedServices = new ArrayList<>();

    @jakarta.persistence.Transient
    private List<Service> requestedServices = new ArrayList<>();

    @jakarta.persistence.Transient
    private List<Service> accessibleServices = new ArrayList<>();

    public enum Role {
        ADMIN, USER;

        Role() {
        }
    }
}
