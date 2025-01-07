package com.example.capstone.exception;
/**
 * Custom exception class to handle bad request scenarios.
 *
 * This exception is thrown when the client sends an invalid or malformed request
 * that cannot be processed by the server. It extends RuntimeException, allowing
 * the application to handle bad requests gracefully without requiring explicit
 * exception handling at every method call.
 *
 * By throwing this exception, the application can return an HTTP 400 Bad Request
 * response to the client, indicating that the request could not be fulfilled due
 * to client-side errors.
 *
 * @author Xiwen Chen
 * @date January 6, 2025
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
