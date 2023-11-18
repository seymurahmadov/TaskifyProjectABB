package com.example.taskifyproject.service;

import com.example.taskifyproject.dto.response.OrganizationResponseDto;

public interface OrganizationService {


    OrganizationResponseDto addUser(Long organizationId, Long userId);
}
