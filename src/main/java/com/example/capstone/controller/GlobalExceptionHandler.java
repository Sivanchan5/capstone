package com.example.capstone.controller;

import com.example.capstone.exception.ResourceNotFoundException;
import com.example.capstone.exception.UnauthorizedException;
import com.example.capstone.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
/**
 * GlobalExceptionHandler class handles exceptions that occur throughout the application.
 * This class uses @ControllerAdvice to provide centralized exception handling across all controllers.
 * Specific handlers for different types of exceptions ensure proper responses with appropriate HTTP status codes.
 *
 * The goal is to provide user-friendly error messages and avoid unhandled exceptions reaching the client.
 *
 * @author Xiwen Chen
 * @date Dec 31, 2024
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles ResourceNotFoundException by returning a 404 Not Found response.
     *
     * @param ex The ResourceNotFoundException that was thrown.
     * @return ResponseEntity containing the error message and HTTP status 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    /**
     * Handles UnauthorizedException by returning a 401 Unauthorized response.
     *
     * @param ex The UnauthorizedException that was thrown.
     * @return ResponseEntity containing the error message and HTTP status 401.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
    /**
     * Handles BadRequestException by returning a 400 Bad Request response.
     *
     * @param ex The BadRequestException that was thrown.
     * @return ResponseEntity containing the error message and HTTP status 400.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    /**
     * Handles MethodArgumentNotValidException, which occurs when validation on an argument fails.
     * Returns the first validation error encountered with a 400 Bad Request status.
     *
     * @param ex The MethodArgumentNotValidException that was thrown during validation.
     * @return ResponseEntity containing the validation error message and HTTP status 400.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid input");
        return ResponseEntity.badRequest().body("Validation Error: " + errorMessage);
    }
    /**
     * Handles BindException, which occurs when there are issues with binding request parameters.
     * Returns the first binding error encountered with a 400 Bad Request status.
     *
     * @param ex The BindException that was thrown during parameter binding.
     * @return ResponseEntity containing the bind error message and HTTP status 400.
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleBindException(BindException ex) {
        String errorMessage = ex.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid parameter");
        return ResponseEntity.badRequest().body("Bind Error: " + errorMessage);
    }
    /**
     * Handles MaxUploadSizeExceededException when file uploads exceed the configured maximum size.
     * Returns a 400 Bad Request response with an appropriate error message.
     *
     * @param e The MaxUploadSizeExceededException that was thrown.
     * @return ResponseEntity containing the error message and HTTP status 400.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException e) {
        return ResponseEntity.badRequest().body("File size exceeds the limit!");
    }
    /**
     * Handles generic exceptions that do not match any specific handler.
     * Returns a 500 Internal Server Error response and logs the stack trace.
     *
     * @param ex The generic Exception that was thrown.
     * @return ResponseEntity containing the error message and HTTP status 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error: " + ex.getMessage());
    }
}
