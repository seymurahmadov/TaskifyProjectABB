package com.example.taskifyproject.service;

import com.example.taskifyproject.dto.request.UserRequestDto;
import com.example.taskifyproject.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAll();

    UserResponseDto create(UserRequestDto userRequestDto);

    UserResponseDto getUserById(Long id);


//    List<UserResponseDto> findAll();
//
//    UserResponseDto findUserByEmail(String email);
//
//    UserResponseDto findUserById(Long id);



}
