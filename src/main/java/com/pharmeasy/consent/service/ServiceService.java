package com.pharmeasy.consent.service;

import com.pharmeasy.consent.dto.ServiceDto;

import java.util.List;

public interface ServiceService {

    // ─────────────────────────────────────
    // Service CRUD
    // ─────────────────────────────────────

    ServiceDto createService(ServiceDto serviceDto);

    List<String> getAllServices();

    ServiceDto getServiceByName(String serviceName);

    ServiceDto updateService(ServiceDto serviceDto);

    String deleteService(String serviceName);

    String whoIsOwnerToService(String serviceName);


    // ─────────────────────────────────────
    // Requester Operations
    // ─────────────────────────────────────

    List<String> whatAreMyRequestedServices(String employeeEmail);

    List<String> whatAreMyAccessibleServices(String employeeEmail);

    Boolean requestServiceAccess(String serviceName, String employeeEmail);

    Boolean removeServiceAccess(String serviceName, String employeeEmail);

    Boolean isServiceAccessGranted(String serviceName, String employeeEmail);


    // ─────────────────────────────────────
    // Owner Operations
    // ─────────────────────────────────────

    List<String> whatAreMyServices(String employeeEmail);

    Boolean isServiceOwnedByMe(String serviceName, String employeeEmail);

    String transferServiceOwnership(String serviceName, String newOwnerEmail);

    Boolean addServiceAccess(String serviceName, String employeeEmail);

    List<String> whoHasRequestedMyService(String serviceName, String ownerEmail);

    List<String> whoHasAccessToMyService(String serviceName, String ownerEmail);

    Boolean grantServiceAccess(String serviceName, String employeeEmail);

    Boolean revokeServiceAccess(String serviceName, String employeeEmail);
}
