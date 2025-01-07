package com.example.capstone.repository;

import com.example.capstone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repository interface for managing User entities.
 * Provides CRUD operations anc custom query methods for User data.
 * Extends JpaRepository to leverage Spring Data JPA functionalities.
 @Author: Xiwen Chen
 Date: Dec 26, 2024
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);//Custom Query to find by email
    Optional<User> findByUserName(String userName);
}
