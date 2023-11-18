package com.example.taskifyproject.service.impl;

import com.example.taskifyproject.dto.response.OrganizationResponseDto;
import com.example.taskifyproject.entity.OrganizationEntity;
import com.example.taskifyproject.entity.UserEntity;
import com.example.taskifyproject.exception.MethodArgumentNotValidException;
import com.example.taskifyproject.mapper.OrganizationMapper;
import com.example.taskifyproject.repository.OrganizationRepository;
import com.example.taskifyproject.repository.UserRepository;
import com.example.taskifyproject.service.OrganizationService;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final OrganizationMapper organizationMapper;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, UserRepository userRepository, OrganizationMapper organizationMapper) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.organizationMapper = organizationMapper;
    }



    @Override
    public OrganizationResponseDto addUser(Long organizationId, Long userId) {
        OrganizationEntity organizationEntity = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new MethodArgumentNotValidException("Data not found with this ID"));

        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        userEntity.setOrganization(organizationEntity);
        userRepository.save(userEntity);

       return organizationMapper.mapEntityToResponseDto(organizationEntity); // !
    }
}
