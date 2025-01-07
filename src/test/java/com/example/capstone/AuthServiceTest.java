package com.example.capstone;
import com.example.capstone.dto.LoginRequest;
import com.example.capstone.dto.RegisterRequest;
import com.example.capstone.dto.UserDto;
import com.example.capstone.model.Role;
import com.example.capstone.model.User;
import com.example.capstone.repository.UserRepository;
import com.example.capstone.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthService
 */
public class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        authService = new AuthService(userRepository);
    }

    @Test
    void testRegisterUser_Success() {
        RegisterRequest request = new RegisterRequest(
                "john_doe",
                "password123",
                "john@example.com",
                "ADMIN"
        );

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserDto registeredUser = authService.registerUser(request);

        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.username()).isEqualTo("john_doe");
        assertThat(registeredUser.role()).isEqualTo(Role.ADMIN);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_DefaultRole() {
        RegisterRequest request = new RegisterRequest(
                "jane_doe",
                "password123",
                "jane@example.com",
                null
        );

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserDto registeredUser = authService.registerUser(request);

        assertThat(registeredUser.role()).isEqualTo(Role.USER);
    }

    @Test
    void testLoginUser_Success() {
        User existingUser = new User(
                "john_doe",
                "john@example.com",
                new BCryptPasswordEncoder().encode("password123"),
                Role.USER
        );

        when(userRepository.findByUserName("john_doe"))
                .thenReturn(Optional.of(existingUser));

        LoginRequest loginRequest = new LoginRequest("john_doe", "password123");
        UserDto loggedInUser = authService.loginUser(loginRequest);

        assertThat(loggedInUser).isNotNull();
        assertThat(loggedInUser.username()).isEqualTo("john_doe");
    }

    @Test
    void testLoginUser_UserNotFound() {
        when(userRepository.findByUserName("unknown_user"))
                .thenReturn(Optional.empty());

        LoginRequest loginRequest = new LoginRequest("unknown_user", "password123");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.loginUser(loginRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Invalid username or password");
    }

    @Test
    void testLoginUser_InvalidPassword() {
        User existingUser = new User(
                "john_doe",
                "john@example.com",
                new BCryptPasswordEncoder().encode("password123"),
                Role.USER
        );

        when(userRepository.findByUserName("john_doe"))
                .thenReturn(Optional.of(existingUser));

        LoginRequest loginRequest = new LoginRequest("john_doe", "wrongpassword");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.loginUser(loginRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Invalid username or password");
    }

    @Test
    void testLoadUserByUsername_Success() {
        User existingUser = new User(
                "admin_user",
                "admin@example.com",
                new BCryptPasswordEncoder().encode("admin123"),
                Role.ADMIN
        );

        when(userRepository.findByUserName("admin_user"))
                .thenReturn(Optional.of(existingUser));

        var userDetails = authService.loadUserByUsername("admin_user");

        assertThat(userDetails.getUsername()).isEqualTo("admin_user");
        assertThat(userDetails.getAuthorities()).hasSize(1);
        assertThat(userDetails.getAuthorities().iterator().next().getAuthority()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        when(userRepository.findByUserName("unknown_user"))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            authService.loadUserByUsername("unknown_user");
        });

        assertThat(exception.getMessage()).isEqualTo("User not found");
    }
}
