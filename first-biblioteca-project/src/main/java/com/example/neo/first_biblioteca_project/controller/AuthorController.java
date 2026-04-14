package com.example.neo.first_biblioteca_project.controller;

import com.example.neo.first_biblioteca_project.dto.AuthorRequestDTO;
import com.example.neo.first_biblioteca_project.dto.AuthorResponseDTO;
import com.example.neo.first_biblioteca_project.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Authors", description = "Endpoints para gerenciamento de autores")
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Lista todos os autores")
    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @Operation(summary = "Busca um autor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable UUID id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @Operation(summary = "Cadastra um novo autor")
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody @Valid AuthorRequestDTO dto) {
        AuthorResponseDTO response = authorService.createAuthor(dto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Atualiza um autor existente")
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(@PathVariable UUID id,
                                          @RequestBody @Valid AuthorRequestDTO authorRequestDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(id, authorRequestDTO));
    }

    @Operation(summary = "Deleta um autor existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
