package com.example.taskifyproject.entity;


import com.example.taskifyproject.enumuration.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organization")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ADMIN;

    private String username;

    private String email;

    private String password;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserEntity> users;

    @OneToMany(mappedBy = "organizationEntity")
    @JsonIgnore
    private List<TaskEntity>  taskEntities;

}
