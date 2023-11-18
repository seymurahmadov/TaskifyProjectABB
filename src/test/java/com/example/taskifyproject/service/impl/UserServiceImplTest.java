package com.example.taskifyproject.service.impl;

import com.example.taskifyproject.dto.response.UserResponseDto;
import com.example.taskifyproject.entity.UserEntity;
import com.example.taskifyproject.mapper.UserMapper;
import com.example.taskifyproject.repository.OrganizationRepository;
import com.example.taskifyproject.repository.TaskRespository;
import com.example.taskifyproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private TaskRespository taskRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getAllTest() {
        // Arrange
        List<UserEntity> userEntities = new ArrayList<>();
        // Add sample UserEntity objects to the list

        when(userRepository.findAll()).thenReturn(userEntities);

        List<UserResponseDto> expectedDtos = new ArrayList<>();
        // Add expected UserResponseDto objects to the list

        when(userMapper.mapEntityListToResponseDtoList(userEntities)).thenReturn(expectedDtos);

        // Act
        List<UserResponseDto> result = userService.getAll();

        // Assert
        assertEquals(expectedDtos, result);
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).mapEntityListToResponseDtoList(userEntities);
    }

    @Test
    void getUserById() {
        // Arrange
        Long userId = 1L;

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        UserResponseDto expectedDto = new UserResponseDto();
        // Set expected values in the UserResponseDto

        when(userMapper.mapEntityToResponseDto(userEntity)).thenReturn(expectedDto);

        // Act
        UserResponseDto result = userService.getUserById(userId);

        // Assert
        assertEquals(expectedDto, result);
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).mapEntityToResponseDto(userEntity);

    }
}