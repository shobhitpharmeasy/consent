package com.pharmeasy.consent.mapper;

import com.pharmeasy.consent.dto.ServiceDto;
import com.pharmeasy.consent.entity.Service;
import com.pharmeasy.consent.mapper.helper.ServiceMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ServiceMapperHelper.class)
public interface ServiceMapper {
    
    @Mapping(source = "createdBy.email", target = "createdByEmail")
    ServiceDto toDto(Service service);

    @Mapping(source = "createdByEmail", target = "createdBy.email")
    @Mapping(target = "deleted", ignore = true)
    Service toEntity(ServiceDto dto);

    List<ServiceDto> toDtoList(List<Service> services);

    List<Service> toEntityList(List<ServiceDto> serviceDtos);
}
