package com.realmofvalyron.ms_auth.controller;

import com.realmofvalyron.ms_auth.dto.AuthResponse;
import com.realmofvalyron.ms_auth.dto.LoginRequest;
import com.realmofvalyron.ms_auth.dto.RegisterRequest;
import com.realmofvalyron.ms_auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse created = authService.register(request);
        return ResponseEntity.created(java.net.URI.create("/api/v1/auth/register")).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}