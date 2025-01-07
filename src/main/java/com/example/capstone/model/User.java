package com.example.capstone.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entity class representing a User in the system.
@Author: Xiwen Chen
Date: Dec 25, 2024
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Username cannot be blank")
    @Column(nullable = false)
    private String userName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message="Password cannot be blank")
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    // 使用枚举映射到数据库表
    @Column(nullable = false)
    private Role role;
    //Default constructor
    public User(){
    }
    public User(Long id) {
        this.id = id;
    }

    //Parameterized constructor
    public User(String userName, String email, String password,Role role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;

    }
    //Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Username cannot be blank") String getUserName() {
        return userName;
    }

    public void setUserName(@NotBlank(message = "Username cannot be blank") String userName) {
        this.userName = userName;
    }

    public @NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password cannot be blank") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be blank") String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<?extends GrantedAuthority> getAuthorities(){
        return List.of(()->"ROLE_USER");
    }
    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
