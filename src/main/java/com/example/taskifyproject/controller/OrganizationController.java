package com.example.taskifyproject.controller;

import com.example.taskifyproject.dto.request.OrganizationRequestDto;
import com.example.taskifyproject.exception.handler.SuccessDetails;
import com.example.taskifyproject.service.impl.OrganizationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/organization")
@SecurityRequirement(name = "Bearer Authentication")
public class OrganizationController {

    private final OrganizationServiceImpl organizationService;

    public OrganizationController(OrganizationServiceImpl organizationService) {
        this.organizationService = organizationService;
    }


    @PostMapping("/add-user/org-id={orgId}/user-id={userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SuccessDetails<String>> addUser(@PathVariable Long orgId, @PathVariable Long userId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            System.out.println("User has role: " + authentication);

        organizationService.addUser(orgId, userId);
        return  ResponseEntity.ok(new SuccessDetails<>("User add to Organization Successfully!", HttpStatus.OK.value(),true));
    }


}
