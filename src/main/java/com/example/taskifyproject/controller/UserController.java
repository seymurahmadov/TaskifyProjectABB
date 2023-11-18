package com.example.taskifyproject.controller;

import com.example.taskifyproject.dto.request.UserRequestDto;
import com.example.taskifyproject.dto.response.UserResponseDto;
import com.example.taskifyproject.exception.handler.SuccessDetails;
import com.example.taskifyproject.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<SuccessDetails<List<UserResponseDto>>> getAll(){
        return  ResponseEntity.ok(new SuccessDetails<>(userService.getAll(), HttpStatus.OK.value(),true));
    }

    @PostMapping
    @Operation(summary = "Create user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SuccessDetails<String>> create(@Valid @RequestBody UserRequestDto userRequestDto){
         userService.create(userRequestDto);
        return ResponseEntity.ok(new SuccessDetails<>("User save Successfully!", HttpStatus.OK.value(),true));
    }

    @PostMapping("/{id}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<SuccessDetails<UserResponseDto>> findById(@PathVariable Long id){
        UserResponseDto userById = userService.getUserById(id);
        return  ResponseEntity.ok(new SuccessDetails<>(userService.getUserById(id), HttpStatus.OK.value(),true));
    }
}
