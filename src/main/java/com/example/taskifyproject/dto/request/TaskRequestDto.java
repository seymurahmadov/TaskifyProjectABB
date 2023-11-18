package com.example.taskifyproject.dto.request;

import com.example.taskifyproject.enumuration.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TaskRequestDto {
    @NotEmpty(message = "The title should not be empty!")
    @NotNull(message = "The title should not be null!")
    private String title;

    @NotEmpty(message = "The description should not be empty!")
    @NotNull(message = "The description should not be null!")
    private String description;

    @NotNull(message = "The deadline should not be empty!")
    private LocalDate deadLine;

    @NotNull(message = "The task type should not be empty!")
    private TaskStatus taskType;

    @NotEmpty(message = "The assign user should not be empty!")
    private List<Long> assignedUserIds;

    @NotNull(message = "The organization id should not be null!")
    private Long organizationId;

}
