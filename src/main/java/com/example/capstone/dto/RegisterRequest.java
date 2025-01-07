package com.example.capstone.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
/**
 * DTO (Data Transfer Object) for handling user registration requests.
 *
 * This record captures the data required to register a new user, ensuring
 * that all fields are validated according to specified constraints.
 *
 * Fields:
 * - username: The desired username for the new account (cannot be blank).
 * - password: The user's chosen password (cannot be blank).
 * - email: The user's email address (must be in valid email format).
 * - role: The role assigned to the user (e.g., "USER" or "ADMIN", cannot be blank).
 *
 * Validation annotations are used to enforce proper input, ensuring that invalid
 * or incomplete registration attempts are handled gracefully.
 *
 * @author Xiwen Chen
 * @date Dec 27, 2025
 */
public record RegisterRequest(
        @NotBlank(message = "Username cannot be blank") String username,
        @NotBlank(message = "Password cannot be blank")
        String password,
        @Email(message = "Invalid email format") String email,
        @NotBlank(message = "Role cannot be blank") String role
) {
}
