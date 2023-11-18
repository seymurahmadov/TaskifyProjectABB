package com.example.taskifyproject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@NotEmpty(message = "The mail should not be empty!")
	@Email(message = "Email is not valid")
	private String email;

	@NotEmpty(message = "The password should not be empty!")
    @Size(min = 6,message = "The password must be at least 8 digits")
	private String password;

	@NotEmpty(message = "Role should not be empty!")
	private String role;

}