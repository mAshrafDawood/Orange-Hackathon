package com.example.orangehackathon.controller;

import com.example.orangehackathon.dto.UserAuth;
import com.example.orangehackathon.entity.User;
import com.example.orangehackathon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/reg")
    public ResponseEntity<?> registerUser(@RequestBody(required = false) User user){
        return userService.registerUser(user);
    }

    @PostMapping(value = "/login")
    public void login(@RequestBody UserAuth userAuth) {
    }
}
