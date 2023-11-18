package com.example.taskifyproject.service.impl;

import com.example.taskifyproject.dto.response.OrganizationResponseDto;
import com.example.taskifyproject.entity.OrganizationEntity;
import com.example.taskifyproject.entity.UserEntity;
import com.example.taskifyproject.mapper.OrganizationMapper;
import com.example.taskifyproject.repository.OrganizationRepository;
import com.example.taskifyproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceImplTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationMapper organizationMapper;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    @Test
    void addUserTest() {
        Long organizationId = 1L;
        Long userId = 2L;

        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId(organizationId);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organizationEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        OrganizationResponseDto expectedDto = new OrganizationResponseDto();

        when(organizationMapper.mapEntityToResponseDto(organizationEntity)).thenReturn(expectedDto);

        OrganizationResponseDto result = organizationService.addUser(organizationId, userId);

        assertEquals(expectedDto, result);
        verify(organizationRepository, times(1)).findById(organizationId);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userEntity);
        verify(organizationMapper, times(1)).mapEntityToResponseDto(organizationEntity);
    }


}