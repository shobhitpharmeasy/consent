package com.pharmeasy.consent.mapper;

import com.pharmeasy.consent.dto.UserConsentLogDto;
import com.pharmeasy.consent.entity.UserConsentLog;
import com.pharmeasy.consent.mapper.helper.UserMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapperHelper.class)
public interface UserConsentLogMapper {

    @Mapping(source = "user.email", target = "userEmail")
    UserConsentLogDto toDto(UserConsentLog log);

    @Mapping(source = "userEmail", target = "user.email")
    @Mapping(target = "deleted", ignore = true)
    UserConsentLog toEntity(UserConsentLogDto dto);

    List<UserConsentLogDto> toDtoList(List<UserConsentLog> logs);

    List<UserConsentLog> toEntityList(List<UserConsentLogDto> dtos);
}
