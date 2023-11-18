package com.example.taskifyproject.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrganizationRequestDto {
    @NotEmpty(message = "The name should not be empty!")
    @NotNull(message = "The name should not be null!")
    private String name;

    @NotEmpty(message = "The phone number should not be empty!")
    @NotNull(message = "The phone number should not be null!")
    private String phoneNumber;

    @NotEmpty(message = "The address should not be empty!")
    @NotNull(message = "The address number should not be null!")
    private String address;
}
