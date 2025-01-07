package com.example.capstone.exception;
/**
 * Custom exception class to handle unauthorized access attempts.
 *
 * This exception is thrown when a user attempts to access a resource or perform an
 * action that they do not have the appropriate permissions or authentication for.
 * It extends RuntimeException, allowing the application to propagate the error and
 * return a 401 Unauthorized or 403 Forbidden response to the client.
 *
 * Example use cases:
 * - Attempting to access admin routes without admin privileges.
 * - Performing actions that require authentication without logging in.
 * - Accessing restricted resources with insufficient roles or permissions.
 *
 * Throwing this exception ensures that unauthorized access attempts are caught
 * and handled gracefully, providing appropriate feedback to the user or client.
 *
 * @author Xiwen Chen
 * @date January 6, 2025
 */

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
