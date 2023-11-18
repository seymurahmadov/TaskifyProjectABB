package com.example.taskifyproject.mapper;

import com.example.taskifyproject.dto.request.TaskRequestDto;
import com.example.taskifyproject.dto.response.TaskResponseDto;
import com.example.taskifyproject.entity.TaskEntity;
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
public interface TaskMapper {
    TaskEntity mapRequestDtoToEntity(TaskRequestDto taskRequestDto);

    TaskResponseDto mapEntityToResponseDto(TaskEntity taskEntity);

    List<TaskResponseDto> mapEntityListToResponseDtoList(List<TaskEntity> taskEntity);

    void update(@MappingTarget TaskEntity taskEntity, TaskRequestDto taskRequestDto);

}
