package com.example.capstone.repository;

import com.example.capstone.model.Adoption;
import com.example.capstone.model.AdoptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Adoption entities.
 * Facilitates database interactions for adoption records,
 * including custom queries to retrieve adoption by user ID.
 * Extends JpaRepository for CRUD functionality.
 * @Author:Xiwen Chen
 * @Date: Dec 26, 2024
 */

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long>{
    List<Adoption>findByUserId(Long userId);//Custom query to get adoptions by user ID
    // Custom Query - Find adoption by status
    List<Adoption> findByStatus(AdoptionStatus status);
}
