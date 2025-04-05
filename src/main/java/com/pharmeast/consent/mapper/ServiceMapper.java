package com.pharmeast.consent.mapper;


@org.mapstruct.Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    builder = @org.mapstruct.Builder(disableBuilder = false),
// 👈 important if you want to explicitly enable builder
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_DEFAULT
)
public interface ServiceMapper {

    com.pharmeast.consent.dto.ServiceDto toDto(
        com.pharmeast.consent.entity.Service employee
    );

    com.pharmeast.consent.entity.Service toEntity(
        com.pharmeast.consent.dto.ServiceDto employeeDto
    );

}
