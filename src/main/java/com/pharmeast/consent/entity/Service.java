package com.pharmeast.consent.entity;

@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "services")
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class Service {

    @jakarta.persistence.Id
    @jakarta.persistence.Column(name = "service_name", unique = true)
    @jakarta.validation.constraints.NotBlank(message = "Service name is mandatory")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[a-zA-Z0-9\\s]+$",
        message = "Service name must contain only letters, numbers, and spaces"
    )
    private String name;

    @jakarta.persistence.Column(name = "service_description")
    @jakarta.validation.constraints.NotBlank(message = "Service description is mandatory")
    @jakarta.validation.constraints.Pattern(
        regexp = "^[a-zA-Z0-9\\s.,!?]+$",
        message = "Service description contains invalid characters"
    )
    private String description;

    @jakarta.persistence.Column(name = "service_status")
    @jakarta.validation.constraints.NotNull(message = "Service status is mandatory")
    private Boolean status;

    @jakarta.persistence.ManyToOne(
        fetch = jakarta.persistence.FetchType.LAZY,
        cascade = jakarta.persistence.CascadeType.REMOVE
    )
    @jakarta.persistence.JoinColumn(name = "created_by")
    @jakarta.validation.constraints.NotNull(message = "Created by is mandatory")
    private Employee createdBy;

    @jakarta.persistence.ElementCollection
    @jakarta.persistence.CollectionTable(
        name = "service_access",
        joinColumns = @jakarta.persistence.JoinColumn(name = "service_name")
    )
    @jakarta.persistence.MapKeyJoinColumn(name = "employee_email")
    @jakarta.persistence.Column(name = "access_status")
    private java.util.Map< Employee, Integer > employeeAccess = new java.util.HashMap<>();
}
