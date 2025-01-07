package com.example.capstone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
/**
 * Configuration class for Spring Security.
 * Manages the security configurations, including request authorization, login, and logout handling.
 * This class ensures that certain paths are restricted to admin users and others are accessible to all users.
 *
 * @author Xiwen Chen
 * @date Dec 30, 2024
 */
@Configuration
public class SecurityConfig {
    /**
     * Configures the security filter chain to manage request authorization,
     * CSRF protection, login, logout, and remember-me functionality.
     *
     * @param http HttpSecurity object for configuring security settings.
     * @return SecurityFilterChain A configured security filter chain.
     * @throws Exception if an error occurs while configuring security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/", "/dogs", "/shelters","/auth/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/adoption/list", "/adoption/apply", "/adoption/cancel/**").authenticated()
                        .requestMatchers("/dogs/list", "/shelters/list", "/adoptions/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()

                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/", true)
                        .loginProcessingUrl("/auth/login")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                );
        return http.build();
    }
    /**
     * Provides a password encoder bean using BCrypt hashing algorithm.
     * This encoder is used to securely hash user passwords.
     *
     * @return BCryptPasswordEncoder A password encoder for hashing passwords.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
