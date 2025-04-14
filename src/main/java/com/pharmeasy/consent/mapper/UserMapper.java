package com.pharmeasy.consent.mapper;

import com.pharmeasy.consent.dto.UserDto;
import com.pharmeasy.consent.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserConsentLogMapper.class})
public interface UserMapper {

    @Mapping(target = "deleted", ignore = true)
    User toEntity(UserDto dto);

    UserDto toDto(User entity);

    List<UserDto> toDtoList(List<User> users);

    List<User> toEntityList(List<UserDto> userDtos);
}
