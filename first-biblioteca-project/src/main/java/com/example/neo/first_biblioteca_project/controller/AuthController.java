package com.example.neo.first_biblioteca_project.controller;

import com.example.neo.first_biblioteca_project.dto.LoginRequestDTO;
import com.example.neo.first_biblioteca_project.dto.LoginResponseDTO;
import com.example.neo.first_biblioteca_project.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Endpoints de autenticação")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Realiza o login e retorna o token JWT")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        LoginResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registra um novo usuário")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid LoginRequestDTO dto) {
        authService.register(dto);
        return ResponseEntity.status(201).build();
    }
}