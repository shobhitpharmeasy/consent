package com.pharmeasy.consent.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLJoinTableRestriction;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

import static com.pharmeasy.consent.utils.Constants.regexEmail;

@Audited
@Entity
@Table(name = "employees")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE employees SET is_deleted = true WHERE email = ?")
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
            regexp = regexEmail, message = "Email must be a valid pharmeasy.in address"
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

    @Builder.Default
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Service> ownedServices = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "service_access", joinColumns = @JoinColumn(name = "employee_email"),
            inverseJoinColumns = @JoinColumn(name = "service_name")
    )
    @SQLJoinTableRestriction("access_status = 1")
    private List<Service> requestedServices = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "service_access", joinColumns = @JoinColumn(name = "employee_email"),
            inverseJoinColumns = @JoinColumn(name = "service_name")
    )
    @SQLJoinTableRestriction("access_status = 2")
    private List<Service> accessibleServices = new ArrayList<>();

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @Version
    @Column(name = "version")
    private Long version;


    public enum Role {
        ADMIN, USER;

        Role() {
        }
    }
}
