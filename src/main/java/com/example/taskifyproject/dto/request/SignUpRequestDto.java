package com.example.taskifyproject.dto.request;

import com.example.taskifyproject.enumuration.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequestDto {
    // validation

    @NotEmpty(message = "The organization name should not be empty!")
    private String orgName;

    @NotEmpty(message = "The phone number should not be empty!")
    private String phoneNumber;

    @NotEmpty(message = "The address should not be empty!")
    private String address;

    @NotEmpty(message = "The username should not be empty!")
    private String username;

    @NotEmpty(message = "The mail should not be empty!")
    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty(message = "The password should not be empty!")
    @Size(min = 6,message = "The password must be at least 8 digits")
    private String password;

    @NotNull(message = "The role should not be null!")
    private Role role;
}
