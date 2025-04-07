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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public ServiceDto createService(final ServiceDto serviceDto) {
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

        final Employee creator = employeeRepository.findById(serviceDto.getCreatedByEmail()).orElseThrow(() -> {
            log.warn("Creator not found: {}", serviceDto.getCreatedByEmail());
            return new RuntimeException(serviceDto.getCreatedByEmail());
        });

        final Service service = serviceMapper.toEntity(serviceDto);
        service.setCreatedBy(creator);

        final Map<Employee, AccessStatus> accessMap = new HashMap<>();
        for (final Map.Entry<String, AccessStatus> entry : serviceDto.getEmployeeAccess().entrySet()) {
            final Employee emp = employeeRepository.findById(entry.getKey()).orElseThrow(() -> {
                log.warn("Employee not found for access mapping: {}", entry.getKey());
                return new RuntimeException(entry.getKey());
            });
            accessMap.put(emp, entry.getValue());
        }
        service.setEmployeeAccess(accessMap);

        final Service saved = serviceRepository.save(service);
        log.info("Service created successfully: {}", saved.getName());
        return serviceMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllServices() {
        log.debug("Fetching all service names");
        return serviceRepository.findAll().stream().map(Service::getName).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceDto getServiceByName(final String serviceName) {
        log.info("Fetching service by name: {}", serviceName);
        return serviceRepository.findById(serviceName).map(serviceMapper::toDto).orElseThrow(() -> {
            log.warn("Service not found: {}", serviceName);
            return new RuntimeException(serviceName);
        });
    }

    @Override
    @Transactional
    public ServiceDto updateService(final ServiceDto serviceDto) {
        log.info("Updating service: {}", serviceDto.getName());
        if (serviceDto.getName() == null) {
            throw new RuntimeException("Service name is required");
        }

        final Service existing = serviceRepository.findById(serviceDto.getName()).orElseThrow(() -> {
            log.warn("Service not found for update: {}", serviceDto.getName());
            return new RuntimeException(serviceDto.getName());
        });

        if (serviceDto.getDescription() != null) {
            existing.setDescription(serviceDto.getDescription());
        }
        if (serviceDto.getStatus() != null) {
            existing.setStatus(serviceDto.getStatus());
        }
        if (serviceDto.getCreatedByEmail() != null) {
            final Employee creator = employeeRepository.findById(serviceDto.getCreatedByEmail()).orElseThrow(() -> {
                log.warn("Creator not found: {}", serviceDto.getCreatedByEmail());
                return new RuntimeException(serviceDto.getCreatedByEmail());
            });
            existing.setCreatedBy(creator);
        }
        if (serviceDto.getEmployeeAccess() != null) {
            final Map<Employee, AccessStatus> updatedMap = new HashMap<>();
            for (final Map.Entry<String, AccessStatus> entry : serviceDto.getEmployeeAccess().entrySet()) {
                final Employee emp = employeeRepository.findById(entry.getKey()).orElseThrow(() -> {
                    log.warn("Employee not found for access update: {}", entry.getKey());
                    return new RuntimeException(entry.getKey());
                });
                updatedMap.put(emp, entry.getValue());
            }
            existing.setEmployeeAccess(updatedMap);
        }

        final Service updated = serviceRepository.save(existing);
        log.info("Service updated successfully: {}", updated.getName());
        return serviceMapper.toDto(updated);
    }

    @Override
    @Transactional
    public String deleteService(final String serviceName) {
        log.info("Deleting service: {}", serviceName);
        final Service service = serviceRepository.findById(serviceName).orElseThrow(() -> {
            log.warn("Service not found for deletion: {}", serviceName);
            return new RuntimeException(serviceName);
        });
        serviceRepository.delete(service);
        return "Service deleted successfully with name: " + serviceName;
    }

    @Override
    @Transactional(readOnly = true)
    public String whoIsOwnerToService(final String serviceName) {
        log.debug("Fetching owner of service: {}", serviceName);
        return serviceRepository.findById(serviceName).map(s -> s.getCreatedBy().getEmail()).orElseThrow(
            () -> new RuntimeException(serviceName));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> whatAreMyRequestedServices(final String email) {
        log.debug("Fetching requested services for: {}", email);
        return employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email)).getRequestedServices()
                                 .stream().map(Service::getName).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> whatAreMyAccessibleServices(final String email) {
        log.debug("Fetching accessible services for: {}", email);
        return employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email)).getAccessibleServices()
                                 .stream().map(Service::getName).toList();
    }

    @Override
    @Transactional
    public Boolean requestServiceAccess(final String serviceName, final String email) {
        log.info("Requesting service access: service={}, employee={}", serviceName, email);
        final Service service = serviceRepository.findById(serviceName).orElseThrow(
            () -> new RuntimeException(serviceName));
        final Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));

        final AccessStatus currentStatus = service.getEmployeeAccess().get(emp);
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
    @Transactional
    public Boolean removeServiceAccess(final String serviceName, final String email) {
        log.info("Removing service access: service={}, employee={}", serviceName, email);
        final Service service = serviceRepository.findById(serviceName).orElseThrow(
            () -> new RuntimeException(serviceName));
        final Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));

        if (service.getEmployeeAccess().remove(emp) != null) {
            serviceRepository.save(service);
            log.info("Service access removed");
            return true;
        }
        log.info("No existing access found to remove");
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isServiceAccessGranted(final String serviceName, final String email) {
        final Service service = serviceRepository.findById(serviceName).orElseThrow(
            () -> new RuntimeException(serviceName));
        final Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));
        final boolean granted = AccessStatus.ACCEPTED.equals(service.getEmployeeAccess().get(emp));
        log.debug("Access granted for service={} and employee={}: {}", serviceName, email, granted);
        return granted;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> whatAreMyServices(final String email) {
        log.debug("Fetching owned services for employee: {}", email);
        return employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email)).getOwnedServices()
                                 .stream().map(Service::getName).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isServiceOwnedByMe(final String serviceName, final String email) {
        final boolean isOwner = whoIsOwnerToService(serviceName).equals(email);
        log.debug("Is service owned by {}: {}", email, isOwner);
        return isOwner;
    }

    @Override
    @Transactional
    public String transferServiceOwnership(final String serviceName, final String newOwnerEmail) {
        log.info("Transferring ownership of service={} to newOwner={}", serviceName, newOwnerEmail);
        final Service service = serviceRepository.findById(serviceName).orElseThrow(
            () -> new RuntimeException(serviceName));
        final Employee newOwner = employeeRepository.findById(newOwnerEmail).orElseThrow(
            () -> new RuntimeException(newOwnerEmail));
        service.setCreatedBy(newOwner);
        serviceRepository.save(service);
        log.info("Service ownership transferred successfully");
        return "Service ownership transferred successfully to: " + newOwnerEmail;
    }

    @Override
    @Transactional
    public Boolean addServiceAccess(final String serviceName, final String email) {
        log.info("Granting service access: service={}, employee={}", serviceName, email);
        final Service service = serviceRepository.findById(serviceName).orElseThrow(
            () -> new RuntimeException(serviceName));
        final Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));

        final AccessStatus currentStatus = service.getEmployeeAccess().get(emp);
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
    @Transactional(readOnly = true)
    public List<String> whoHasRequestedMyService(final String serviceName, final String ownerEmail) {
        if (Boolean.FALSE.equals(isServiceOwnedByMe(serviceName, ownerEmail))) {
            log.warn("Unauthorized access to requesters list by: {}", ownerEmail);
            throw new RuntimeException("You are not the owner of this service");
        }
        final Service service = serviceRepository.findById(serviceName).orElseThrow(
            () -> new RuntimeException(serviceName));
        return service.getEmployeeAccess().entrySet().stream().filter(
            entry -> entry.getValue() == AccessStatus.REQUESTED).map(entry -> entry.getKey().getEmail()).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> whoHasAccessToMyService(final String serviceName, final String ownerEmail) {
        if (Boolean.FALSE.equals(isServiceOwnedByMe(serviceName, ownerEmail))) {
            log.warn("Unauthorized access to access list by: {}", ownerEmail);
            throw new RuntimeException("You are not the owner of this service");
        }
        final Service service = serviceRepository.findById(serviceName).orElseThrow(
            () -> new RuntimeException(serviceName));
        return service.getEmployeeAccess().entrySet().stream().filter(
            entry -> entry.getValue() == AccessStatus.ACCEPTED).map(entry -> entry.getKey().getEmail()).toList();
    }

    @Override
    @Transactional
    public Boolean grantServiceAccess(final String serviceName, final String email) {
        return addServiceAccess(serviceName, email);
    }

    @Override
    @Transactional
    public Boolean revokeServiceAccess(final String serviceName, final String email) {
        return removeServiceAccess(serviceName, email);
    }
}
