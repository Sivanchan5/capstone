package com.example.capstone.service;

import com.example.capstone.dto.RegisterRequest;
import com.example.capstone.dto.UserDto;
import com.example.capstone.exception.ResourceNotFoundException;
import com.example.capstone.model.Role;
import com.example.capstone.model.User;
import com.example.capstone.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for User entity.
 * Handles business logic and validation for user management.
 * @Author: Xiwen Chen
 * Date: Dec 27,2024
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Create User with validation
    @Transactional
    public UserDto createUser(@Valid RegisterRequest request) {
        if(userRepository.findByEmail(request.email()).isPresent()){
            throw new ValidationException("Email is already registered");
        }
        Role role = request.role() != null ? Role.valueOf(request.role()) : Role.USER;// 默认用户角色
        User user = new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),  // 密码加密
                role

        );
        userRepository.save(user);
        return new UserDto(user);
    }
    //Read to find all users
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    //Read to find user by ID
    public Optional<User> getUserById(Long id){
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with ID " + id + " not found.")));
    }
    //Update User
    public Optional<User> updateUser(Long id, @Valid User updateUser){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found."));
        user.setUserName(updateUser.getUserName());
        user.setEmail(updateUser.getEmail());
        user.setPassword(updateUser.getPassword());
        return Optional.of(userRepository.save(user));

    }
    //Delete User
    public Optional<Void> deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found."));
        userRepository.delete(user);
        return Optional.empty();
    }
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
   public Optional<User> findUserByUsername(String username){
        return userRepository.findByUserName(username);
    }


}
