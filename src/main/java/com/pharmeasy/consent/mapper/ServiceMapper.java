package com.pharmeasy.consent.mapper;


@org.mapstruct.Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    builder = @org.mapstruct.Builder(disableBuilder = false),
// 👈 important if you want to explicitly enable builder
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_DEFAULT
)
public interface ServiceMapper {

    com.pharmeasy.consent.dto.ServiceDto toDto(
        com.pharmeasy.consent.entity.Service employee
    );

    com.pharmeasy.consent.entity.Service toEntity(
        com.pharmeasy.consent.dto.ServiceDto employeeDto
    );

}
