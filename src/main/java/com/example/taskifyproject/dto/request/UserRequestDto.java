package com.example.taskifyproject.dto.request;

import com.example.taskifyproject.enumuration.Role;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRequestDto {
//    @NotEmpty(message = "The name should not be empty!")
    private String name;

//    @NotEmpty(message = "The surname should not be empty!")
    private String surname;

//    @NotEmpty(message = "The email should not be empty!")
//    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

//    @NotEmpty(message = "The password should not be empty!")
//    @Size(min = 6,message = "The password must be at least 8 digits")
    private String password;

    private Role role;

//    @NotEmpty(message = "Organization id should not be empty!")
    private Long organizationId;
//    private Long taskIds;

}
