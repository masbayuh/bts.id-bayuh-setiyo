package com.coding_backend_v2_bayuh_setiyo.demo.controller;

import com.coding_backend_v2_bayuh_setiyo.demo.dto.AuthResponse;
import com.coding_backend_v2_bayuh_setiyo.demo.dto.LoginRequest;
import com.coding_backend_v2_bayuh_setiyo.demo.dto.RegisterRequest;
import com.coding_backend_v2_bayuh_setiyo.demo.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Registrasi berhasil"));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }
}
