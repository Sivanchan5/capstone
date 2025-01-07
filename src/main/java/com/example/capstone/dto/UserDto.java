package com.example.capstone.dto;

import com.example.capstone.model.Role;
import com.example.capstone.model.User;
/**
 * DTO (Data Transfer Object) for representing user information.
 *
 * This record is used to transfer essential user data between different
 * layers of the application, providing a simplified view of the User entity.
 *
 * Fields:
 * - id: The unique identifier for the user.
 * - username: The username associated with the user.
 * - email: The user's email address.
 * - role: The role assigned to the user (e.g., "USER" or "ADMIN").
 *
 * Constructors:
 * - UserDto(User userEntity): Constructs a UserDto from a User entity, copying its properties.
 * - UserDto(String username, String email): Simplified constructor for creating a UserDto with
 *   default role set to USER, primarily for lightweight operations that don't require an ID.
 *
 * This class facilitates the separation of concerns by decoupling the internal
 * User entity from external representations, enhancing security and modularity.
 *
 * @author Xiwen Chen
 * @date Dec 27, 2024
 */
public record UserDto(
        Long id,
        String username,
        String email,
        Role role
) {
    public UserDto(User userEntity) {
        this(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getRole()
        );
    }
    // Constructs a UserDto with username and email, defaulting the role to USER.
    public UserDto(String username, String email) {
        this(null, username, email, Role.USER);  // Default role is USER
    }
    // Returns the role of the user.
    public Role getRole() {
        return this.role;
    }
}