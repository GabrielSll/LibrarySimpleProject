package com.example.neo.first_biblioteca_project.controller;

import com.example.neo.first_biblioteca_project.dto.AuthorResponseDTO;
import com.example.neo.first_biblioteca_project.dto.PublisherRequestDTO;
import com.example.neo.first_biblioteca_project.dto.PublisherResponseDTO;
import com.example.neo.first_biblioteca_project.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Publishers", description = "Endpoints para gerenciamento de editoras")
@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Operation(summary = "Lista todas as editoras")
    @GetMapping
    public ResponseEntity<Page<PublisherResponseDTO>> getAllPublishers(@PageableDefault (
            size = 10, sort = "name")Pageable pageable) {
        return ResponseEntity.ok(publisherService.getAllPublishers(pageable));}

    @Operation(summary = "Busca uma editora por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> getPublisherById(@PathVariable UUID id) {
        return ResponseEntity.ok(publisherService.getPublisherById(id));
    }

    @Operation(summary = "Cadastra uma nova editora")
    @PostMapping
    public ResponseEntity<PublisherResponseDTO> createPublisher(@RequestBody @Valid PublisherRequestDTO dto) {
        PublisherResponseDTO response = publisherService.createPublisher(dto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Atualiza uma editora existente")
    @PutMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> updatePublisher(@PathVariable UUID id,
                                                @RequestBody @Valid PublisherRequestDTO dto) {
        return ResponseEntity.ok(publisherService.updatePublisher(id, dto));
    }

    @Operation(summary = "Deleta uma editora existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable UUID id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
