package com.example.taskifyproject.dto.response;

import com.example.taskifyproject.entity.OrganizationEntity;
import com.example.taskifyproject.entity.UserEntity;
import com.example.taskifyproject.enumuration.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private TaskStatus taskType;
    private List<UserEntity> assignees;
    private OrganizationEntity organizationEntity;


}
