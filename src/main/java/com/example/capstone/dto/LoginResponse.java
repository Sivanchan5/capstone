package com.example.capstone.dto;
/**
 * DTO (Data Transfer Object) for representing the response of a login request.
 *
 * This record contains a simple message field that conveys the result of the login attempt,
 * such as "Login successful" or "Invalid username or password".
 *
 * The `LoginResponse` DTO is used to provide feedback to the client after processing a login request.
 *
 * @author Xiwen Chen
 * @date Dec 27, 2024
 */
public record LoginResponse(
        String message
) {}
