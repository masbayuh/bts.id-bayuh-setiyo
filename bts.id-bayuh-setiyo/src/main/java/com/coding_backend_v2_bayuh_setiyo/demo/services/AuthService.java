package com.coding_backend_v2_bayuh_setiyo.demo.services;

import com.coding_backend_v2_bayuh_setiyo.demo.dto.AuthResponse;
import com.coding_backend_v2_bayuh_setiyo.demo.dto.LoginRequest;
import com.coding_backend_v2_bayuh_setiyo.demo.dto.RegisterRequest;
import com.coding_backend_v2_bayuh_setiyo.demo.entity.User;
import com.coding_backend_v2_bayuh_setiyo.demo.repository.UserRepository;
import com.coding_backend_v2_bayuh_setiyo.demo.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest req) {
        if (!req.password().equals(req.passwordConfirmation())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "password dan password_confirmation tidak sama");
        }
        if (userRepository.existsByUsername(req.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username sudah digunakan");
        }
        User user = new User();
        user.setUsername(req.username());
        user.setPassword(passwordEncoder.encode(req.password()));
        userRepository.save(user);
    }

    public AuthResponse login(@Valid LoginRequest req) {
        User user = userRepository.findByUsername(req.username())
                .filter(u -> passwordEncoder.matches(req.password(), u.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "username atau password salah"));

        return new AuthResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user));
    }
}
