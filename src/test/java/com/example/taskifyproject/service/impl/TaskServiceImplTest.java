package com.example.taskifyproject.service.impl;

import com.example.taskifyproject.dto.request.TaskRequestDto;
import com.example.taskifyproject.dto.response.TaskResponseDto;
import com.example.taskifyproject.entity.OrganizationEntity;
import com.example.taskifyproject.entity.TaskEntity;
import com.example.taskifyproject.entity.UserEntity;
import com.example.taskifyproject.exception.MethodArgumentNotValidException;
import com.example.taskifyproject.mapper.TaskMapper;
import com.example.taskifyproject.mapper.UserMapper;
import com.example.taskifyproject.repository.OrganizationRepository;
import com.example.taskifyproject.repository.TaskRespository;
import com.example.taskifyproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRespository taskRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MailServiceImpl mailService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserDetails userDetails;




    @Test
    void createTest() {
        // Arrange
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setAssignedUserIds(List.of(1L, 2L));
        taskRequestDto.setOrganizationId(1L);

        OrganizationEntity organizationEntity = new OrganizationEntity();
        when(organizationRepository.findById(taskRequestDto.getOrganizationId())).thenReturn(Optional.of(organizationEntity));

        List<UserEntity> assignedUsers = new ArrayList<>();
        assignedUsers.add(new UserEntity());
        assignedUsers.add(new UserEntity());
        when(userRepository.findAllById(taskRequestDto.getAssignedUserIds())).thenReturn(assignedUsers);

        TaskEntity taskEntity = new TaskEntity();
        when(taskMapper.mapRequestDtoToEntity(taskRequestDto)).thenReturn(taskEntity);

        // Act
        taskService.create(taskRequestDto);

        // Assert
        verify(organizationRepository).findById(taskRequestDto.getOrganizationId());
        verify(userRepository).findAllById(taskRequestDto.getAssignedUserIds());
        verify(taskMapper).mapRequestDtoToEntity(taskRequestDto);
        verify(taskRepository).save(taskEntity);
    }

    @Test
    void findById_WithExistingTaskId_ShouldReturnTaskResponseDto() {
        // Arrange
        Long taskId = 1L;

        TaskEntity taskEntity = new TaskEntity();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        TaskResponseDto expectedResponseDto = new TaskResponseDto();
        when(taskMapper.mapEntityToResponseDto(taskEntity)).thenReturn(expectedResponseDto);

        // Act
        TaskResponseDto result = taskService.findById(taskId);

        // Assert
        assertEquals(expectedResponseDto, result);
        verify(taskRepository).findById(taskId);
        verify(taskMapper).mapEntityToResponseDto(taskEntity);
    }

    @Test
    void findByIdTest() {
        // Arrange
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MethodArgumentNotValidException.class, () -> taskService.findById(taskId));
        verify(taskRepository).findById(taskId);
    }

    @Test
    void deleteTest() {
        // Arrange
        Long taskId = 1L;

        TaskEntity taskEntity = new TaskEntity();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        // Act
        taskService.delete(taskId);

        // Assert
        verify(taskRepository).findById(taskId);
        verify(taskRepository).delete(taskEntity);
    }

    @Test
    void updateTest() {
        // Arrange
        Long taskId = 1L;

        TaskEntity taskEntity = new TaskEntity();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setAssignedUserIds(List.of(1L, 2L));

        // Act
        taskService.update(taskId, taskRequestDto);

        // Assert
        verify(taskRepository).findById(taskId);
        verify(taskMapper).update(taskEntity, taskRequestDto);
    }


}