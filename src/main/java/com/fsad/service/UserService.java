package com.fsad.service;

import com.fsad.model.User;
import com.fsad.security.*;
import com.fsad.repository.UserRepository;
import com.fsad.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil; // ✅ ADD THIS

    public String register(User user) {

        if (repo.findByEmail(user.getEmail()) != null) {
            return "User already exists";
        }

        repo.save(user);
        return "Registered successfully";
    }

    public Object login(String email, String password) {

        // ✅ ADMIN LOGIN
        if (email.equals("admin@gmail.com") && password.equals("admin123")) {

            String token = jwtUtil.generateToken(email); // ✅ FIXED

            return new AuthResponse(token, "admin", email);
        }

        // ✅ STUDENT LOGIN
        User user = repo.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {

            String token = jwtUtil.generateToken(email); // ✅ FIXED

            return new AuthResponse(token, "student", email);
        }

        return null;
    }
}