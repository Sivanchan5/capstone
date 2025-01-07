package com.example.capstone.controller;

import com.example.capstone.dto.RegisterRequest;
import com.example.capstone.dto.UserDto;
import com.example.capstone.exception.ResourceNotFoundException;
import com.example.capstone.model.User;
import com.example.capstone.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
/**
 * UserController handles HTTP requests related to user management and operations.
 *
 * This controller provides endpoints for CRUD operations on users, including:
 * - Retrieving all users
 * - Retrieving, updating, and deleting users by ID
 * - Searching users by email or username
 * - Creating new users (typically by an admin)
 *
 * This class leverages the UserService to handle business logic and user-related processes.
 * API responses are returned as ResponseEntity objects to provide proper HTTP status codes.
 *
 * @author Xiwen Chen
 * @date Dec 27, 2025
 */
@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;

    }

    //Get all users(API)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    //Get a specific user
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found."));
        return ResponseEntity.ok(user);
    }
    //Update user information
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User updatedUser = userService.updateUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found."));
        return ResponseEntity.ok(updatedUser);
    }
    //Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found."));
        return ResponseEntity.ok("User deleted successfully.");
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email of the user to search for.
     * @return ResponseEntity containing the user if found, or a 404 status if not.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to search for.
     * @return ResponseEntity containing the user if found, or a 404 status if not.
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findUserByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * Creates a new user, typically initiated by an admin.
     *
     * @param request The registration request containing user details.
     * @return ResponseEntity containing the created user DTO.
     */
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody RegisterRequest request) {
        UserDto createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }

}
