package com.example.taskifyproject.mapper;

import com.example.taskifyproject.dto.request.UserRequestDto;
import com.example.taskifyproject.dto.response.UserResponseDto;
import com.example.taskifyproject.entity.UserEntity;
import com.example.taskifyproject.enumuration.Role;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {


    UserEntity mapRequestDtoToEntity(UserRequestDto userRequestDto);

    UserResponseDto mapEntityToResponseDto(UserEntity userEntity);

    List<UserResponseDto> mapEntityListToResponseDtoList(List<UserEntity> userEntities);

    void update(@MappingTarget UserEntity userEntity, UserRequestDto userRequestDto);
}
