package com.example.taskifyproject.repository;

import com.example.taskifyproject.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
    boolean existsByEmail(String email);
    OrganizationEntity findOrganizationEntityByEmail(String email);

}
