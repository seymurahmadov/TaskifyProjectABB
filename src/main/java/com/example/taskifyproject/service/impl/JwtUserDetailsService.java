package com.example.taskifyproject.service.impl;



import com.example.taskifyproject.entity.OrganizationEntity;
import com.example.taskifyproject.entity.UserEntity;
import com.example.taskifyproject.repository.OrganizationRepository;
import com.example.taskifyproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (organizationRepository.existsByEmail(email)) {
            return new User(organizationRepository.findOrganizationEntityByEmail(email).getEmail(), organizationRepository.findOrganizationEntityByEmail(email).getPassword(),
                    new ArrayList<>());
        } else if(userRepository.existsByEmail(email)){
            return new User(userRepository.findUsersEntityByEmail(email).getEmail(), userRepository.findUsersEntityByEmail(email).getPassword(),
                    new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

}