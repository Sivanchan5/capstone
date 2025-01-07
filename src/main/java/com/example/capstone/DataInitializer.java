package com.example.capstone;

import com.example.capstone.model.Role;
import com.example.capstone.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
/**
 * DataInitializer is a component responsible for initializing default data
 * when the application starts. Specifically, it checks if an admin account
 * exists, and if not, creates one with predefined credentials.
 *
 * This class implements the CommandLineRunner interface, which allows
 * executing code after the application context is loaded.
 *
 * Key Responsibilities:
 * - Ensure there is at least one admin account in the system.
 * - Automatically encode the admin's password using the PasswordEncoder.
 *
 * @author Xiwen Chen
 * @date Jan 1, 2025
 */
@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }
    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByUserName("admin").isEmpty()) {
            com.example.capstone.model.User admin = new com.example.capstone.model.User(
                    "admin",
                    "admin@all4paws.com",
                    passwordEncoder.encode("admin123"),
                    Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Admin account created");
        }
    }
}
