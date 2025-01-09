package com.example.capstone.config;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @ParameterizedTest
    @CsvSource({
            "password123, password123",
            "helloWorld, helloWorld",
            "strongPass2023, strongPass2023"
    })
    void testPasswordEncoder(String rawPassword, String inputPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assertThat(passwordEncoder.matches(inputPassword, encodedPassword)).isTrue();
    }
}
