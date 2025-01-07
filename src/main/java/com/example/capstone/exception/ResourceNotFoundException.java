package com.example.capstone.exception;
/**
 * Custom exception class to handle scenarios where a requested resource is not found.
 *
 * This exception is thrown when an entity or record requested by the client does not
 * exist in the database or system. It extends RuntimeException, allowing the application
 * to propagate the error and return a 404 Not Found response to the client.
 *
 * Throwing this exception ensures that the application can gracefully handle
 * missing resources and inform the client that the requested data could not be located.
 *
 * @author Xiwen Chen
 * @date January 6, 2025
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
