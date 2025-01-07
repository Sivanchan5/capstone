package com.example.capstone.repository;

import com.example.capstone.model.Dog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Dog entities.
 * Supports standard database operations and additional queries
 * to filter dogs based on specific attributes such as breed.
 * Extends JpaRepository to utilize Spring Data JPA functionalities.
 @Author: Xiwen Chen
 Date: Dec 26, 2024
 */
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    List<Dog> findByBreed(String breed); //Custom query to find dogs by breed

}
