package com.pharmeasy.consent.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {

    @Id
    @Column(name = "service_name", unique = true)
    @NotBlank(message = "Service name is mandatory")
    @Pattern(
        regexp = "^[a-zA-Z0-9\\s]+$", message = "Service name must contain only letters, numbers, and spaces"
    )
    private String name;

    @Column(name = "service_description")
    @NotBlank(message = "Service description is mandatory")
    @Pattern(
        regexp = "^[a-zA-Z0-9\\s.,!?]+$", message = "Service description contains invalid characters"
    )
    private String description;

    @Column(name = "service_status")
    @NotNull(message = "Service status is mandatory")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "created_by")
    @NotNull(message = "Created by is mandatory")
    private Employee createdBy;


    @ElementCollection
    @CollectionTable(
        name = "service_access", joinColumns = @JoinColumn(name = "service_name")
    )
    @MapKeyJoinColumn(name = "employee_email")
    @Column(name = "access_status")
    @Enumerated(EnumType.ORDINAL)
    private Map<Employee, AccessStatus> employeeAccess = new HashMap<>();


    public enum AccessStatus {
        NOT_ALLOWED,  // 0
        REQUESTED,    // 1
        ACCEPTED;      // 2

        AccessStatus() {
        }
    }
}
