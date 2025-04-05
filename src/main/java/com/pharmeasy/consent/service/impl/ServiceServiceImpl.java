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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl
    implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public ServiceDto createService(ServiceDto serviceDto) {
        List<String> missingFields = new ArrayList<>();

        if (serviceDto.getName() == null) missingFields.add("name");
        if (serviceDto.getDescription() == null) missingFields.add("description");
        if (serviceDto.getCreatedByEmail() == null) missingFields.add("createdBy");
        if (serviceDto.getStatus() == null) missingFields.add("status");
        if (serviceDto.getEmployeeAccess() == null) missingFields.add("employeeAccess");

        if (!missingFields.isEmpty()) {
            throw new RuntimeException("Missing required fields: " + String.join(", ", missingFields));
        }

        Employee creator = employeeRepository.findById(serviceDto.getCreatedByEmail()).orElseThrow(
            () -> new RuntimeException(serviceDto.getCreatedByEmail()));

        Service service = serviceMapper.toEntity(serviceDto);
        service.setCreatedBy(creator);

        // Map email to Employee entity
        Map<Employee, AccessStatus> accessMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : serviceDto.getEmployeeAccess().entrySet()) {
            Employee emp = employeeRepository.findById(entry.getKey()).orElseThrow(
                () -> new RuntimeException(entry.getKey()));
            accessMap.put(emp, AccessStatus.values()[entry.getValue()]);
        }

        service.setEmployeeAccess(accessMap);
        return serviceMapper.toDto(serviceRepository.save(service));
    }

    @Override
    public List<String> getAllServices() {
        return serviceRepository.findAll().stream().map(Service::getName).toList();
    }

    @Override
    public ServiceDto getServiceByName(String serviceName) {
        return serviceRepository.findById(serviceName).map(serviceMapper::toDto).orElseThrow(
            () -> new RuntimeException(serviceName));
    }

    @Override
    public ServiceDto updateService(ServiceDto serviceDto) {
        if (serviceDto.getName() == null) throw new RuntimeException("Service name is required");

        Service existing = serviceRepository.findById(serviceDto.getName()).orElseThrow(
            () -> new RuntimeException(serviceDto.getName()));

        if (serviceDto.getDescription() != null) existing.setDescription(serviceDto.getDescription());
        if (serviceDto.getStatus() != null) existing.setStatus(serviceDto.getStatus());
        if (serviceDto.getCreatedByEmail() != null) {
            Employee creator = employeeRepository.findById(serviceDto.getCreatedByEmail()).orElseThrow(
                () -> new RuntimeException(serviceDto.getCreatedByEmail()));
            existing.setCreatedBy(creator);
        }

        if (serviceDto.getEmployeeAccess() != null) {
            Map<Employee, AccessStatus> updatedMap = new HashMap<>();
            for (Map.Entry<String, Integer> entry : serviceDto.getEmployeeAccess().entrySet()) {
                Employee emp = employeeRepository.findById(entry.getKey()).orElseThrow(
                    () -> new RuntimeException(entry.getKey()));
                updatedMap.put(emp, AccessStatus.values()[entry.getValue()]);
            }
            existing.setEmployeeAccess(updatedMap);
        }

        return serviceMapper.toDto(serviceRepository.save(existing));
    }

    @Override
    public String deleteService(String serviceName) {
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        serviceRepository.delete(service);
        return "Service deleted successfully with name: " + serviceName;
    }

    @Override
    public String whoIsOwnerToService(String serviceName) {
        return serviceRepository.findById(serviceName).map(s -> s.getCreatedBy().getEmail()).orElseThrow(
            () -> new RuntimeException(serviceName));
    }

    @Override
    public List<String> whatAreMyRequestedServices(String email) {
        return employeeRepository.findRequestedServicesByEmployeeEmail(email).stream().map(Service::getName).toList();
    }

    @Override
    public List<String> whatAreMyAccessibleServices(String email) {
        return employeeRepository.findAccessibleServicesByEmployeeEmail(email).stream().map(Service::getName).toList();
    }

    @Override
    public Boolean requestServiceAccess(String serviceName, String email) {
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));

        AccessStatus currentStatus = service.getEmployeeAccess().get(emp);
        if (currentStatus == AccessStatus.REQUESTED || currentStatus == AccessStatus.ACCEPTED) {
            return false;
        }
        service.getEmployeeAccess().put(emp, AccessStatus.REQUESTED);
        serviceRepository.save(service);
        return true;
    }

    @Override
    public Boolean removeServiceAccess(String serviceName, String email) {
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));
        if (service.getEmployeeAccess().remove(emp) != null) {
            serviceRepository.save(service);
            return true;
        }
        return false;
    }

    @Override
    public Boolean isServiceAccessGranted(String serviceName, String email) {
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));
        return AccessStatus.ACCEPTED.equals(service.getEmployeeAccess().get(emp));
    }

    @Override
    public List<String> whatAreMyServices(String email) {
        return employeeRepository.findByEmailWithOwnedServices(email).orElseThrow(() -> new RuntimeException(email))
                                 .getOwnedServices().stream().map(Service::getName).toList();
    }

    @Override
    public Boolean isServiceOwnedByMe(String serviceName, String email) {
        return whoIsOwnerToService(serviceName).equals(email);
    }

    @Override
    public String transferServiceOwnership(String serviceName, String newOwnerEmail) {
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        Employee newOwner = employeeRepository.findById(newOwnerEmail).orElseThrow(
            () -> new RuntimeException(newOwnerEmail));
        service.setCreatedBy(newOwner);
        serviceRepository.save(service);
        return "Service ownership transferred successfully to: " + newOwnerEmail;
    }

    @Override
    public Boolean addServiceAccess(String serviceName, String email) {
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));
        Employee emp = employeeRepository.findById(email).orElseThrow(() -> new RuntimeException(email));

        AccessStatus currentStatus = service.getEmployeeAccess().get(emp);
        if (currentStatus == AccessStatus.ACCEPTED) return false;

        service.getEmployeeAccess().put(emp, AccessStatus.ACCEPTED);
        serviceRepository.save(service);
        return true;
    }

    @Override
    public List<String> whoHasRequestedMyService(String serviceName, String ownerEmail) {
        if (Boolean.FALSE.equals(isServiceOwnedByMe(serviceName, ownerEmail))) {
            throw new RuntimeException("You are not the owner of this service");
        }
        Service service = serviceRepository.findById(serviceName).orElseThrow(() -> new RuntimeException(serviceName));

        return service.getEmployeeAccess().entrySet().stream().filter(
            entry -> entry.getValue() == AccessStatus.REQUESTED).map(entry -> entry.getKey().getEmail()).toList();
    }

    @Override
    public List<String> whoHasAccessToMyService(String serviceName, String ownerEmail) {
        if (Boolean.FALSE.equals(isServiceOwnedByMe(serviceName, ownerEmail))) {
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
