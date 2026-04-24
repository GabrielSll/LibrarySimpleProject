package com.example.neo.first_biblioteca_project.controller;

import com.example.neo.first_biblioteca_project.dto.AuthorRequestDTO;
import com.example.neo.first_biblioteca_project.dto.AuthorResponseDTO;
import com.example.neo.first_biblioteca_project.exception.ResourceNotFoundException;
import com.example.neo.first_biblioteca_project.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAuthorWhenFound() throws Exception {
        //ARRANGE
        UUID id = UUID.randomUUID();

        AuthorResponseDTO response = new AuthorResponseDTO(id, "Malcolm Gladwell");

        when(authorService.getAuthorById(id)).thenReturn(response);

        //ACT & ASSERT
        // perform() → executa a requisição simulada
        // get("/author/{id}", id) → simula GET /books/{uuid}
        mockMvc.perform(get("/authors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Malcolm Gladwell"));
    }

    @Test
    void shouldReturn404WhenNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(authorService.getAuthorById(id)).thenThrow(new ResourceNotFoundException("Author Not Found"));

        //ACT & ASSERT
        mockMvc.perform(get("/authors/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateAuthorAndReturn201() throws Exception {
        AuthorRequestDTO request = new AuthorRequestDTO("Malcolm Gladwell");

        AuthorResponseDTO response  = new AuthorResponseDTO(UUID.randomUUID(), "Malcolm Gladwell");

        when(authorService.createAuthor(any(AuthorRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Malcolm Gladwell"));
    }

    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {

        AuthorRequestDTO invalidRequest = new AuthorRequestDTO("");

        mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteAuthorAndReturn204() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(authorService).deleteAuthor(id);

        mockMvc.perform(delete("/authors/{id}", id)).andExpect(status().isNoContent());
    }
}
