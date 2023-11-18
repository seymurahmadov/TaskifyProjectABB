package com.example.taskifyproject.repository;

import com.example.taskifyproject.entity.OrganizationEntity;
import com.example.taskifyproject.entity.TaskEntity;
import com.example.taskifyproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRespository extends JpaRepository<TaskEntity,Long> {
    List<TaskEntity> findAllByAssigneesContaining(UserEntity user);

    List<TaskEntity> findByOrganizationEntity(OrganizationEntity organizationEntity);
//    List<TaskEntity> findByAssignees(List<UserEntity> assignees);
}
