package com.example.taskifyproject.repository;

import com.example.taskifyproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
    UserEntity findUsersEntityByEmail(String email);
    boolean existsByEmail(String email);
}
