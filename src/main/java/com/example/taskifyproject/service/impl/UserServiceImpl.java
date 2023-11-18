package com.example.taskifyproject.service.impl;

import com.example.taskifyproject.dto.request.UserRequestDto;
import com.example.taskifyproject.dto.response.UserResponseDto;
import com.example.taskifyproject.entity.OrganizationEntity;
import com.example.taskifyproject.entity.UserEntity;
import com.example.taskifyproject.exception.MethodArgumentNotValidException;
import com.example.taskifyproject.mapper.UserMapper;
import com.example.taskifyproject.repository.OrganizationRepository;
import com.example.taskifyproject.repository.TaskRespository;
import com.example.taskifyproject.repository.UserRepository;
import com.example.taskifyproject.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final TaskRespository taskRespository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, OrganizationRepository organizationRepository, TaskRespository taskRespository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.taskRespository = taskRespository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponseDto> getAll() {
        List<UserEntity> all = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = userMapper.mapEntityListToResponseDtoList(all);
        return userResponseDtos;
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        OrganizationEntity organizationEntity =
                organizationRepository.findById(userRequestDto.getOrganizationId())
                        .orElseThrow(() -> new MethodArgumentNotValidException("Data not found with this id"));

        UserEntity userEntity = userMapper.mapRequestDtoToEntity(userRequestDto);
        userEntity.setOrganization(organizationEntity);
        userRepository.save(userEntity);

        UserResponseDto userResponseDto = userMapper.mapEntityToResponseDto(userEntity);
        return userResponseDto;
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new MethodArgumentNotValidException("Data not found with this ID"));
        UserResponseDto userResponseDto = userMapper.mapEntityToResponseDto(userEntity);
        return userResponseDto;
    }
}
