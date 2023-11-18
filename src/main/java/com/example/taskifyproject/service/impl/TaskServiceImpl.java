package com.example.taskifyproject.service.impl;

import com.example.taskifyproject.dto.request.TaskRequestDto;
import com.example.taskifyproject.dto.response.TaskResponseDto;
import com.example.taskifyproject.entity.OrganizationEntity;
import com.example.taskifyproject.entity.TaskEntity;
import com.example.taskifyproject.entity.UserEntity;
import com.example.taskifyproject.exception.MethodArgumentNotValidException;
import com.example.taskifyproject.exception.ResourceNotFoundException;
import com.example.taskifyproject.mapper.TaskMapper;
import com.example.taskifyproject.mapper.UserMapper;
import com.example.taskifyproject.repository.OrganizationRepository;
import com.example.taskifyproject.repository.TaskRespository;
import com.example.taskifyproject.repository.UserRepository;
import com.example.taskifyproject.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRespository taskRespository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;
    private final MailServiceImpl mailService;

    public TaskServiceImpl(TaskRespository taskRespository, OrganizationRepository organizationRepository, UserRepository userRepository, TaskMapper taskMapper, UserMapper userMapper, MailServiceImpl mailService) {
        this.taskRespository = taskRespository;
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    @Override
    public List<TaskResponseDto> getAll() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (organizationRepository.existsByEmail(userDetails.getUsername())) {
            OrganizationEntity organizationEntity = organizationRepository.findOrganizationEntityByEmail(userDetails.getUsername());

            List<TaskEntity> byOrganizationEntity = taskRespository.findByOrganizationEntity(organizationEntity);
            List<TaskResponseDto> taskResponseDtos = taskMapper.mapEntityListToResponseDtoList(byOrganizationEntity);
            return taskResponseDtos;
        } else {
            UserEntity userEntity = userRepository.findUsersEntityByEmail(userDetails.getUsername());

            List<TaskEntity> taskEntities = taskRespository.findByOrganizationEntity(userEntity.getOrganization());
            List<TaskResponseDto> taskResponseDtos = taskMapper.mapEntityListToResponseDtoList(taskEntities);
            return taskResponseDtos;
        }

    }


    @Override
    public void create(TaskRequestDto taskRequestDto) {

        List<UserEntity> assignedUsers = userRepository.findAllById(taskRequestDto.getAssignedUserIds());
        OrganizationEntity organizationEntity = organizationRepository.findById(taskRequestDto.getOrganizationId()).orElseThrow();
        TaskEntity taskEntity = taskMapper.mapRequestDtoToEntity(taskRequestDto);
        taskEntity.setAssignees(assignedUsers);
        taskEntity.setOrganizationEntity(organizationEntity);

        for (UserEntity userItem : assignedUsers) {
            assignTaskToUser(taskEntity, userItem.getId());
        }


        taskRespository.save(taskEntity);
    }

    @Override
    public TaskResponseDto findById(Long id) {
        TaskEntity taskEntity = taskRespository.findById(id)
                .orElseThrow(() -> new MethodArgumentNotValidException("Data not found with this ID"));
        TaskResponseDto taskResponseDto = taskMapper.mapEntityToResponseDto(taskEntity);
        return taskResponseDto;
    }

    @Override
    public void delete(Long id) {
        TaskEntity taskEntity = taskRespository.findById(id)
                .orElseThrow(() -> new MethodArgumentNotValidException("Data not found with this ID"));
        taskRespository.delete(taskEntity);
    }

    @Override
    public void update(Long id, TaskRequestDto taskRequestDto) {
        TaskEntity taskEntity = taskRespository.findById(id)
                .orElseThrow(() -> new MethodArgumentNotValidException("Data not found with this ID"));
        taskMapper.update(taskEntity, taskRequestDto);

    }


    public void assignTaskToUser(TaskEntity task, Long userId) {
        // Assign the task to the user
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        task.setAssignees(task.getAssignees());
        taskRespository.save(task);

        // Send email notification
        mailService.sendTaskAssignmentNotification(user, task);
    }
}
