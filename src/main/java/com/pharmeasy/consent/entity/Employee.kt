package com.pharmeasy.consent.entity

import com.pharmeasy.consent.utils.Constants
import com.pharmeasy.consent.utils.HashUtils
import com.pharmeasy.consent.utils.RandomUtils
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import lombok.*
import org.hibernate.annotations.SQLJoinTableRestriction

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Employee {
    @Id
    @Column(name = "email", nullable = false, unique = true)
    private var email: @NotBlank(message = "Email is mandatory") @Pattern(
        regexp = Constants.regexEmail,
        message = "Email must be a valid pharmeasy.in address"
    ) String? = null

    @Column(name = "first_name", nullable = false)
    private var firstName: @NotBlank(message = "First name is mandatory") @Pattern(
        regexp = "^[a-zA-Z]+$",
        message = "First name must contain only letters"
    ) String? = null

    @Column(name = "middle_name")
    private var middleName: @Pattern(
        regexp = "^[a-zA-Z]*$",
        message = "Middle name must contain only letters"
    ) String? = null

    @Column(name = "last_name", nullable = false)
    private var lastName: @NotBlank(message = "Last name is mandatory") @Pattern(
        regexp = "^[a-zA-Z]+$",
        message = "Last name must contain only letters"
    ) String? = null

    @Column(name = "password_hash", nullable = false)
    @Builder.Default
    private var passwordHash: @NotBlank(message = "Password hash is mandatory") @Size(
        min = 60,
        max = 60,
        message = "Password hash must be exactly 512 characters"
    ) String? = HashUtils.hash(
        RandomUtils.generateRandomPassword()
    )

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private var role: @NotNull(message = "Role is mandatory") Role? = null

    @Builder.Default
    @OneToMany(mappedBy = "createdBy", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val ownedServices: List<Service> = ArrayList()

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "service_access",
        joinColumns = [JoinColumn(name = "employee_email")],
        inverseJoinColumns = [JoinColumn(name = "service_name")]
    )
    @SQLJoinTableRestriction("access_status = 1")
    private val requestedServices: List<Service> = ArrayList()

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "service_access",
        joinColumns = [JoinColumn(name = "employee_email")],
        inverseJoinColumns = [JoinColumn(name = "service_name")]
    )
    @SQLJoinTableRestriction("access_status = 2")
    private val accessibleServices: List<Service> = ArrayList()

    enum class Role {
        ADMIN, USER
    }
}
