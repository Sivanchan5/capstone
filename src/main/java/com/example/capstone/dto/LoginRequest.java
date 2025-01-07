package com.example.capstone.dto;

import jakarta.validation.constraints.NotBlank;
/**
 * DTO (Data Transfer Object) for handling login requests.
 *
 * This record captures the necessary user input for login, including:
 * - Username
 * - Password
 *
 * Validation constraints are applied to ensure that both fields are not blank.
 * This DTO is primarily used to transfer data between the client and the authentication controller.
 *
 * @author Xiwen Chen
 * @date Dec 27, 2024
 */

public record LoginRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
