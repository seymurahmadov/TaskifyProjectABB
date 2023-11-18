package com.example.taskifyproject.service;

import com.example.taskifyproject.dto.request.TaskRequestDto;
import com.example.taskifyproject.dto.response.TaskResponseDto;

import java.util.List;

public interface TaskService {

    List<TaskResponseDto> getAll();

    void create(TaskRequestDto taskRequestDto);

    TaskResponseDto findById(Long id);

    void delete(Long id);

    void update(Long id, TaskRequestDto taskRequestDto);

}
