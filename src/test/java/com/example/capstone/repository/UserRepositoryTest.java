package com.example.capstone.repository;

import com.example.capstone.model.Role;
import com.example.capstone.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("john_doe", "john@example.com", "password123", Role.USER);
        userRepository.save(testUser);
    }

    @Test
    void testFindByEmail() {
        Optional<User> user = userRepository.findByEmail("john@example.com");
        assertThat(user).isPresent();
        assertThat(user.get().getUserName()).isEqualTo("john_doe");
    }

    @Test
    void testFindByUserName() {
        Optional<User> user = userRepository.findByUserName("john_doe");
        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void testFindByEmail_NotFound() {
        Optional<User> user = userRepository.findByEmail("nonexistent@example.com");
        assertThat(user).isEmpty();
    }
}
