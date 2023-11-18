package com.example.taskifyproject.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationResponseDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private String address;

}
