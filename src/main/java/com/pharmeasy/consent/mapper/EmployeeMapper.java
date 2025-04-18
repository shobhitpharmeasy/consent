package com.pharmeasy.consent.mapper;

import com.pharmeasy.consent.dto.EmployeeDto;
import com.pharmeasy.consent.entity.Employee;
import com.pharmeasy.consent.mapper.helper.EmployeeMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = EmployeeMapperHelper.class)
public interface EmployeeMapper {

    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    Employee toEntity(EmployeeDto employeeDto);

    EmployeeDto toDto(Employee employee);

    List<Employee> toEntityList(List<EmployeeDto> employeeDtos);

    List<EmployeeDto> toDtoList(List<Employee> employees);
}
