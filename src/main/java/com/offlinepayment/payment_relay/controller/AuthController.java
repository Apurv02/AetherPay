package com.offlinepayment.payment_relay.controller;

import com.offlinepayment.payment_relay.auth.LoginRequest;
import com.offlinepayment.payment_relay.auth.LoginResponse;
import com.offlinepayment.payment_relay.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        if (request.getUsername().equals("admin")
                && request.getPassword().equals("admin123")) {

            String token = jwtService.generateToken(request.getUsername());

            return new LoginResponse(token);
        }

        throw new RuntimeException("Invalid Username or Password");
    }
}