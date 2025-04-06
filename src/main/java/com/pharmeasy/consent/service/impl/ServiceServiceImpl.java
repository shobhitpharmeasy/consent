package com.pharmeasy.consent.service.impl;

import com.pharmeasy.consent.dto.ServiceDto;
import com.pharmeasy.consent.entity.Employee;
import com.pharmeasy.consent.entity.Service;
import com.pharmeasy.consent.entity.Service.AccessStatus;
import com.pharmeasy.consent.mapper.ServiceMapper;
import com.pharmeasy.consent.repository.EmployeeRepository;
import com.pharmeasy.consent.repository.ServiceRepository;
import com.pharmeasy.consent.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl
    implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public ServiceDto createService(ServiceDto serviceDto) {
        log.info("Creating service: {}", serviceDto.getName());
        List<String> missingFields = new ArrayList<>();

        if (serviceDto.getName() == null) missingFields.add("name");
        if (serviceDto.getDescription() == null) missingFields.add("description");
        if (serviceDto.getCreatedByEmail() == null) missingFields.add("createdBy");
        if (serviceDto.getStatus() == null) missingFields.add("status");
        if (serviceDto.getEmployeeAccess() == null) missingFields.add("employeeAccess");

        if (!missingFields.isEmpty()) {
            log.warn("Missing required fields while creating service: {}", missingFields);
            throw new RuntimeException("Missing required fields: " + String.join(", ", missingFields));
        }

        Employee creator = employeeRepository.findById(serviceDto.getCreatedByEmail()).orElseThrow(() -> {
            log.warn("Creator not found: {}", serviceDto.getCreatedByEmail());
            return new RuntimeException(serviceDto.getCreatedByEmail());
        });

        Service service = serviceMapper.toEntity(serviceDto);
        service.setCreatedBy(creator);

        Map<Employee, AccessStatus> accessMap = new HashMap<>();
        for (Map.Entry<String, AccessStatus> entry : serviceDto.getEmployeeAccess().entrySet()) {
            Employee emp = employeeRepository.findById(entry.getKey()).orElseThrow(() -> {
                log.warn("Employee not found for access mapping: {}", entry.getKey());
                return new RuntimeException(entry.getKey());
            });
            accessMap.put(emp, entry.getValue());
        }

        service.setEmployeeAccess(accessMap);
        Service saved = serviceRepository.save(service);
        log.info("Service created successfully: {}", saved.getName());
        return serviceMapper.toDto(saved);
    }

    @Override
    public List<String> getAllServices() {
        log.debug("Fetching all service names");
        return serviceRepository.findAll().stream().map(Service::getName).toList();
    }

    @Override
    public ServiceDto getServiceByName(String serviceName) {
        log.info("Fetching service by name: {}", serviceName);
        return serviceRepository.findById(serviceName).map(serviceMapper::toDto).orElseThrow(() -> {
            log.warn("Service not found: {}", serviceName);
            return new RuntimeException(serviceName);
        });
    }

    @Override
    public ServiceDto updateService(ServiceDto serviceDto) {
        log.info("Updating service: {}", serviceDto.getName());
        if (serviceDto.getName() == null) throw new RuntimeException("Service name is required");

        Service existing = serviceRepository.findById(serviceDto.getName()).orElseThrow(() -> {
            log.warn("Service not found for update: {}", serviceDto.getName());
            return new RuntimeException(serviceDto.getName());
        });

        if (serviceDto.getDescription() != null) existing.setDescription(serviceDto.getDescription());
        if (serviceDto.getStatus() != null) existing.setStatus(serviceDto.getStatus());
        if (serviceDto.getCreatedByEmail() != null) {
            Employee creator = employeeRepository.findById(serviceDto.getCreatedByEmail()).orElseThrow(() -> {
                log.warn("Creator not found: {}", serviceDto.getCreatedByEmail());
                return new RuntimeException(serviceDto.getCreatedByEmail());
            });
            existing.setCreatedBy(creator);
        }

        if (serviceDto.getEmployeeAccess() != null) {
            Map<Employee, AccessStatus> updatedMap = new HashMap<>();
            for (Map.Entry<String, AccessStatus> entry : serviceDto.getEmployeeAccess().entrySet()) {
                Employee emp = employeeRepository.findById(entry.getKey()).orElseThrow(() -> {
                    log.warn("Employee not found for access update: {}", entry.getKey());
                    return new RuntimeException(entry.getKey());
                });
                updatedMap.put(emp, entry.getValue());
            }
            existing.setEmployeeAccess(updatedMap);
        }

        Service updated = serviceRepository.save(existing);
        log.info("Service updated successfully: {}", updated.getName());
        return serviceMapper.toDto(updated);
    }

    @Override
    public String deleteService(String serviceName) {
        log.info("Deleting service: {}", serviceName);
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> {
            log.warn("Service not found for deletion: {}", serviceName);
            return new RuntimeException(serviceName);
        });
        serviceRepository.delete(service);
        return "Service deleted successfully with name: " + serviceName;
    }

    @Override
    public String whoIsOwnerToService(String serviceName) {
        log.debug("Fetching owner of service: {}", serviceName);
        return serviceRepository.findById(serviceName).map(s -> s.getCreatedBy().getEmail()).orElseThrow(
            () -> new RuntimeException(serviceName));
    }

    @Override
    public List<String> whatAreMyRequestedServices(String email) {
        log.debug("Fetching requested services for: {}", email);
        return employeeRepository.findRequestedServicesByEmployeeEmail(email).stream().map(Service::getName).toList();
    }

    @Override
    public List<String> whatAreMyAccessibleServices(String email) {
        log.debug("Fetching accessible services for: {}", email);
        return employeeRepository.findAccessibleServicesByEmployeeEmail(email).stream().map(Service::getName).toList();
    }

    @Override
    public Boolean requestServiceAccess(String serviceName, String email) {
        log.info("Requesting service access: service={}, employee={}", serviceName, email);
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));

        AccessStatus currentStatus = service.getEmployeeAccess().get(emp);
        if (currentStatus == AccessStatus.REQUESTED || currentStatus == AccessStatus.ACCEPTED) {
            log.info("Service access already requested or granted for employee: {}", email);
            return false;
        }

        service.getEmployeeAccess().put(emp, AccessStatus.REQUESTED);
        serviceRepository.save(service);
        log.info("Service access requested successfully");
        return true;
    }

    @Override
    public Boolean removeServiceAccess(String serviceName, String email) {
        log.info("Removing service access: service={}, employee={}", serviceName, email);
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));

        if (service.getEmployeeAccess().remove(emp) != null) {
            serviceRepository.save(service);
            log.info("Service access removed");
            return true;
        }
        log.info("No existing access found to remove");
        return false;
    }

    @Override
    public Boolean isServiceAccessGranted(String serviceName, String email) {
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));
        boolean granted = AccessStatus.ACCEPTED.equals(service.getEmployeeAccess().get(emp));
        log.debug("Access granted for service={} and employee={}: {}", serviceName, email, granted);
        return granted;
    }

    @Override
    public List<String> whatAreMyServices(String email) {
        log.debug("Fetching owned services for employee: {}", email);
        return employeeRepository.findByEmailWithOwnedServices(email).orElseThrow(() -> new RuntimeException(email))
                                 .getOwnedServices().stream().map(Service::getName).toList();
    }

    @Override
    public Boolean isServiceOwnedByMe(String serviceName, String email) {
        boolean isOwner = whoIsOwnerToService(serviceName).equals(email);
        log.debug("Is service owned by {}: {}", email, isOwner);
        return isOwner;
    }

    @Override
    public String transferServiceOwnership(String serviceName, String newOwnerEmail) {
        log.info("Transferring ownership of service={} to newOwner={}", serviceName, newOwnerEmail);
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        Employee newOwner = employeeRepository.findById(newOwnerEmail).orElseThrow(
            () -> new RuntimeException(newOwnerEmail));
        service.setCreatedBy(newOwner);
        serviceRepository.save(service);
        log.info("Service ownership transferred successfully");
        return "Service ownership transferred successfully to: " + newOwnerEmail;
    }

    @Override
    public Boolean addServiceAccess(String serviceName, String email) {
        log.info("Granting service access: service={}, employee={}", serviceName, email);
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));

        AccessStatus currentStatus = service.getEmployeeAccess().get(emp);
        if (currentStatus == AccessStatus.ACCEPTED) {
            log.info("Service access already granted");
            return false;
        }

        service.getEmployeeAccess().put(emp, AccessStatus.ACCEPTED);
        serviceRepository.save(service);
        log.info("Service access granted");
        return true;
    }

    @Override
    public List<String> whoHasRequestedMyService(String serviceName, String ownerEmail) {
        if (Boolean.FALSE.equals(isServiceOwnedByMe(serviceName, ownerEmail))) {
            log.warn("Unauthorized access to requesters list by: {}", ownerEmail);
            throw new RuntimeException("You are not the owner of this service");
        }
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        return service.getEmployeeAccess().entrySet().stream().filter(
            entry -> entry.getValue() == AccessStatus.REQUESTED).map(entry -> entry.getKey().getEmail()).toList();
    }

    @Override
    public List<String> whoHasAccessToMyService(String serviceName, String ownerEmail) {
        if (Boolean.FALSE.equals(isServiceOwnedByMe(serviceName, ownerEmail))) {
            log.warn("Unauthorized access to access list by: {}", ownerEmail);
            throw new RuntimeException("You are not the owner of this service");
        }
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        return service.getEmployeeAccess().entrySet().stream().filter(
            entry -> entry.getValue() == AccessStatus.ACCEPTED).map(entry -> entry.getKey().getEmail()).toList();
    }

    @Override
    public Boolean grantServiceAccess(String serviceName, String email) {
        return addServiceAccess(serviceName, email);
    }

    @Override
    public Boolean revokeServiceAccess(String serviceName, String email) {
        return removeServiceAccess(serviceName, email);
    }
}
