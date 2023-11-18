package com.example.taskifyproject.mapper;

import com.example.taskifyproject.dto.request.OrganizationRequestDto;
import com.example.taskifyproject.dto.response.OrganizationResponseDto;
import com.example.taskifyproject.entity.OrganizationEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface OrganizationMapper {
    OrganizationEntity mapRequestDtoToEntity(OrganizationRequestDto organizationRequestDto);

    OrganizationResponseDto mapEntityToResponseDto(OrganizationEntity organizationEntity);

    List<OrganizationResponseDto> mapEntityListToResponseDtoList(List<OrganizationEntity> organizationEntities);

    void update(@MappingTarget OrganizationEntity organizationEntity, OrganizationRequestDto organizationRequestDto);
}
