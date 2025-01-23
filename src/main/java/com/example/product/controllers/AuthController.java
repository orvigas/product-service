package com.example.product.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.repositories.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> user) {
        log.debug("User login: {}", user);
        Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(user.get("username"), user.get("password")));
        String token = tokenService.generateToken(auth);
        log.debug("JWT token: {}", token);
        return ResponseEntity.ok(Map.of("accessToken", token));
    }

}
