package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.LoginRequest; 
import com.example.demo.dto.AuthResponse; 
import com.example.demo.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth") // Endpoint base
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired 
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {

        // 1. Verificação de unicidade 
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>("Username already taken!", HttpStatus.BAD_REQUEST);
        }

        // 2. Converte DTO para Entity
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());

        // 3. Salva e retorna sucesso
        userService.registerUser(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }
    // Endpoint de Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {

        // 1. Encontra o usuário
        User user;
        try {
            user = userService.findByUsername(loginRequest.getUsername());
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        // 2. Verifica a senha criptografada
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        // 3. Gera o Token JWT
        String token = jwtUtil.generateToken(user.getUsername());

        // 4. Retorna o Token para o cliente
        return ResponseEntity.ok(new AuthResponse(token));
    }
}