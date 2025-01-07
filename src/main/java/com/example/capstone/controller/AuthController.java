package com.example.capstone.controller;

import com.example.capstone.dto.LoginRequest;
import com.example.capstone.dto.LoginResponse;
import com.example.capstone.dto.RegisterRequest;
import com.example.capstone.dto.UserDto;
import com.example.capstone.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 * Controller class for handling user authentication and registration.
 * Manages user login, registration, and logout processes.
 * Provides endpoints for displaying login and registration forms,
 * handling user input, and maintaining session state.
 *
 * @author Xiwen Chen
 * @date Dec 31, 2024
 */

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Display the registration page.
     * Initializes the registration form with default values and
     * passes it to the view.
     *
     * @param model Model to hold form data for the view.
     * @return Returns the registration page (register.html).
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest",new RegisterRequest("","","","USER"));
        return "register";  // 对应 register.html 页面
    }

    /**
     * Handle user registration.
     * Validates the registration form and creates a new user account.
     * Stores the registered user in the session upon success.
     *
     * @param request User registration form data.
     * @param session HTTP session to store user information.
     * @return Redirects to the dashboard page after successful registration,
     *         or returns to the registration page if validation fails.
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerRequest") RegisterRequest request, HttpSession session) {
        if (request == null) {
            return "register";  // 如果校验失败，返回注册页面
        }
        UserDto registeredUser = authService.registerUser(request);
        session.setAttribute("user", registeredUser);  // 注册成功后设置Session
        return "redirect:/dashboard";  // 重定向到仪表盘
    }

    /**
     * Display the login page.
     *
     * @return Returns the login page (login.html).
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    /**
     * Handle user login.
     * Authenticates user credentials and returns a response indicating
     * the success or failure of the login attempt.
     *
     * @param request User login form data.
     * @param session HTTP session to store user information upon successful login.
     * @return Returns a success response if login is successful, or
     *         a 401 (Unauthorized) response if the credentials are invalid.
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> login(@Valid @ModelAttribute LoginRequest request, HttpSession session) {
        UserDto user = authService.loginUser(request);
        if (user != null) {
            session.setAttribute("user", user);  // 登录成功后设置Session
            return ResponseEntity.ok(new LoginResponse("Login successful"));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse("Invalid username or password"));
        }
    }

    /**
     * Display the login page.
     *
     * @return Returns the login page (login.html).
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 销毁Session
        return "redirect:/auth/login";
    }

}
