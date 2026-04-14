package com.example.neo.first_biblioteca_project.controller;

import com.example.neo.first_biblioteca_project.dto.BookRequestDTO;
import com.example.neo.first_biblioteca_project.dto.BookResponseDTO;
import com.example.neo.first_biblioteca_project.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Books", description = "Endpoints para gerenciamento de livros")
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Lista todos os livros")
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Operation(summary = "Busca um livro por ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @Operation(summary = "Cadastra um novo livro")
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookRequestDTO dto) {
        BookResponseDTO response = bookService.createBook(dto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Atualiza um livro existente")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable UUID id,
                                      @RequestBody @Valid BookRequestDTO dto) {
       return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    @Operation(summary = "Deleta um livro existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
