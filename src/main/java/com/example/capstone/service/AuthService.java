package com.example.capstone.service;

import com.example.capstone.dto.LoginRequest;
import com.example.capstone.dto.RegisterRequest;
import com.example.capstone.dto.UserDto;
import com.example.capstone.model.Role;
import com.example.capstone.model.User;
import com.example.capstone.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * Service class responsible for handling user authentication and registration.
 * This class implements the UserDetailsService to provide user details to Spring Security during login.
 * It manages user registration, login, and role assignments.
 *
 * @author Xiwen Chen
 * @date Dec 31, 2024
 */
@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;


    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    // Register
    public UserDto registerUser(@Valid RegisterRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // If role is empty or null，default USER
        List<String> validRoles = List.of("USER", "ADMIN");

        if (request.role() == null || request.role().isBlank() || !validRoles.contains(request.role().toUpperCase())) {
            request = new RegisterRequest(request.username(), request.password(), request.email(), "USER");
        }

        Role role = Role.valueOf(request.role().toUpperCase());
        User user = new User(
                request.username(),
                request.email(),
                encoder.encode(request.password()),
                role
        );

        userRepository.save(user);
        return new UserDto(user);
    }

    // Login
    public UserDto loginUser(@Valid LoginRequest request) {
        User user = userRepository.findByUserName(request.username())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
           BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(request.password(), user.getPassword())) {
            System.out.println("Password matches");
            return new UserDto(user);
        }
        System.out.println("Password does not match");
        throw new RuntimeException("Invalid username or password");
    }
    /**
     * Implements the loadUserByUsername method from UserDetailsService to load user details during authentication.
     * Retrieves the user by username and constructs a UserDetails object for Spring Security.
     *
     * @param username The username of the user to be loaded.
     * @return UserDetails representing the authenticated user.
     * @throws UsernameNotFoundException if the user is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())  // Encrypted password from the database
                .roles(user.getRole().name())  // Assign role to the user
                .build();
    }

}
