package com.pharmeasy.consent.mapper.helper;

import com.pharmeasy.consent.entity.Service;
import com.pharmeasy.consent.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapperHelper {

    private final ServiceRepository serviceRepository;

    public String serviceToString(Service service) {
        return service != null ? service.getName() : null;
    }

    public Service stringToService(String name) {
        if (name == null) return null;
        return serviceRepository.findById(name).orElse(Service.builder().name(name).build());
    }
}
