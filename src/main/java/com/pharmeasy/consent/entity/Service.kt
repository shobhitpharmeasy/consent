package com.pharmeasy.consent.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import lombok.*

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Service {
    @Id
    @Column(name = "service_name", unique = true)
    private var name: @NotBlank(message = "Service name is mandatory") @Pattern(
        regexp = "^[a-zA-Z0-9\\s]+$",
        message = "Service name must contain only letters, numbers, and spaces"
    ) String? = null

    @Column(name = "service_description")
    private var description: @NotBlank(message = "Service description is mandatory") @Pattern(
        regexp = "^[a-zA-Z0-9\\s.,!?]+$",
        message = "Service description contains invalid characters"
    ) String? = null

    @Column(name = "service_status")
    private var status: @NotNull(message = "Service status is mandatory") Boolean? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "created_by")
    private var createdBy: @NotNull(message = "Created by is mandatory") Employee? = null


    @ElementCollection
    @CollectionTable(name = "service_access", joinColumns = [JoinColumn(name = "service_name")])
    @MapKeyJoinColumn(name = "employee_email")
    @Column(name = "access_status")
    @Enumerated(
        EnumType.ORDINAL
    )
    @Builder.Default
    private var employeeAccess: Map<Employee, AccessStatus> = HashMap()


    enum class AccessStatus  // 2
    {
        NOT_ALLOWED,  // 0
        REQUESTED,  // 1
        ACCEPTED
    }
}
