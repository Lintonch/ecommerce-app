package com.ecommerce.auth.auth_service.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auth.auth_service.model.User;
import com.ecommerce.auth.auth_service.repository.UserRepository;
import com.ecommerce.auth.auth_service.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtils;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // User Registration
    @PostMapping("/signup")
    public String registerUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Username is already taken!";
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    // User Login and JWT Generation
    @PostMapping("/login")
    public String authenticateUser(@RequestBody User loginRequest) {
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());

        if (userOpt.isEmpty()) {
            return "User not found!";
        }

        User user = userOpt.get();
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return "Invalid credentials!";
        }


        // Generate JWT
        String jwt = jwtUtils.generateJwtToken(user.getUsername());
        return "Bearer " + jwt;
    }

}
