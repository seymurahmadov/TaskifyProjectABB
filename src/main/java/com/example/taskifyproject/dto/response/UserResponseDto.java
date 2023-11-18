package com.example.taskifyproject.dto.response;

import com.example.taskifyproject.entity.OrganizationEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private OrganizationEntity organizationId;
//    private List<TaskEntity> taskIds;
}
