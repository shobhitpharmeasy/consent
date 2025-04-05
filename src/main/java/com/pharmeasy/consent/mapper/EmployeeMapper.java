package com.pharmeasy.consent.mapper;


import com.pharmeasy.consent.dto.EmployeeDto;
import com.pharmeasy.consent.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    //    @Mappings(
//        {
//            @Mapping(target = "passwordHash", ignore = true), @Mapping(target = "ownedServices", ignore = true)
//        }
//    )
    EmployeeDto toDto(Employee employee);

    //    @Mappings(
//        {
//            @Mapping(target = "passwordHash", ignore = true), @Mapping(target = "ownedServices", ignore = true)
//        }
//    )
    Employee toEntity(EmployeeDto employeeDto);
}
