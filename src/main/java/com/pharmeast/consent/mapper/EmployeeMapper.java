package com.pharmeast.consent.mapper;


@org.mapstruct.Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    builder = @org.mapstruct.Builder(disableBuilder = false),
    // 👈 important if you want to explicitly enable builder
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_DEFAULT
)
public interface EmployeeMapper {

    com.pharmeast.consent.dto.EmployeeDto toDto(
        com.pharmeast.consent.entity.Employee employee
    );

    com.pharmeast.consent.entity.Employee toEntity(
        com.pharmeast.consent.dto.EmployeeDto employeeDto
    );
}
