package com.society.management.controller;

import com.society.management.dto.LoginRequest;
import com.society.management.entity.User;
import com.society.management.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmailAndPassword(
                request.getEmail(),
                request.getPassword()
        );

        if (user == null) {
            return "INVALID_LOGIN";
        }

        return user;
    }
}

