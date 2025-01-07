package com.example.capstone.repository;

import com.example.capstone.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Shelter entities.
 * Handles database operations for shelters, allowing custom queries
 * to find shelters by name or other criteria. Inherits standard
 * CRUD methods from JpaRepository.
 * @Author:Xiwen Chen
 * @Date: Dec 26, 2024
 */
@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Shelter findByName(String name);//Custom query to find a shelter by name
}
