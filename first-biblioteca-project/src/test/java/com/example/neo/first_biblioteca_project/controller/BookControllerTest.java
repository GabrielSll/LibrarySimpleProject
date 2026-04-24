package com.example.neo.first_biblioteca_project.controller;

import com.example.neo.first_biblioteca_project.dto.BookRequestDTO;
import com.example.neo.first_biblioteca_project.dto.BookResponseDTO;
import com.example.neo.first_biblioteca_project.exception.ResourceNotFoundException;
import com.example.neo.first_biblioteca_project.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    // Ferramenta que simula requisições HTTP sem precisar de um servidor rodando.

    @MockitoBean
    private BookService bookService;
    // Cria um mock do BookService e registra no contexto do Spring
    // O Controller vai usar esse mock em vez do Service real

    @Autowired
    private ObjectMapper objectMapper;
    // Converte objetos Java para JSON e vice-versa
    // Usado para montar o body das requisições

    @Test
    void shouldReturnBookWhenFound() throws Exception {
        //ARRANGE
        UUID id = UUID.randomUUID();
        BookResponseDTO response = new BookResponseDTO(
                id, "Outliers", "Sextante", List.of("Malcolm Gladwell"));

        when(bookService.getBookById(id)).thenReturn(response);

        //ACT & ASSERT
        // perform() → executa a requisição simulada
        // get("/books/{id}", id) → simula GET /books/{uuid}
        mockMvc.perform(get("/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Outliers"))
                .andExpect(jsonPath("$.publisherName").value("Sextante"));
    }

    @Test
    void shouldReturn404WhenNotFound() throws Exception {
        //ARRANGE
        UUID id = UUID.randomUUID();

        when(bookService.getBookById(id))
                .thenThrow(new ResourceNotFoundException("Book Not Found"));
        // thenThrow() → configura o mock para lançar uma exceção
        // Simula o cenário onde o livro não existe no banco

        //ACT & ASSERT
        mockMvc.perform(get("/books/{id}", id))
                .andExpect(status().isNotFound());
        // Verifica que o GlobalExceptionHandler interceptou a exceção
        // e retornou 404 corretamente
    }

    @Test
    void shouldCreateBookAndReturn201() throws Exception {
        //ARRRANGE
        UUID publisherId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();

        BookRequestDTO request = new BookRequestDTO(
                "Outliers", publisherId, List.of(authorId));

        BookResponseDTO response = new BookResponseDTO(
                UUID.randomUUID(), "Outliers", "Sextante",
                List.of("Malcolm Gladwell"));

        when(bookService.createBook(any(BookRequestDTO.class))).thenReturn(response);
        // any(BookRequestDTO.class) → aceita qualquer BookRequestDTO
        // necessário porque o objeto criado no teste e o recebido pelo Controller
        // são instâncias diferentes — o .equals() falharia sem o any()

        // ACT & ASSERT
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        // Define o Content-Type como application/json
                        // sem isso o Spring não consegue desserializar o body
                        .content(objectMapper.writeValueAsString(request)))
                // content() → define o body da requisição
                // writeValueAsString() → converte o objeto Java para JSON

                .andExpect(status().isCreated())
                // Verifica que o status é 201
                .andExpect(jsonPath("$.name").value("Outliers"))
                .andExpect(jsonPath("$.publisherName").value("Sextante"));
    }

    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {

        // ARRANGE
        BookRequestDTO invalidRequest = new BookRequestDTO(
                "", UUID.randomUUID(), List.of(UUID.randomUUID()));
        // Nome em branco — deve ser bloqueado pelo @NotBlank

        // ACT & ASSERT
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
        // Verifica que o Bean Validation bloqueou a requisição
        // e o GlobalExceptionHandler retornou 400
    }

    @Test
    void shouldDeleteBookAndReturn204() throws Exception {

        // ARRANGE
        UUID id = UUID.randomUUID();

        doNothing().when(bookService).deleteBook(id);
        // doNothing() → usado para métodos void
        // "quando deleteBook for chamado com esse id, não faz nada"
        // Para métodos void o when().thenReturn() não funciona — usa doNothing()

        // ACT & ASSERT
        mockMvc.perform(delete("/books/{id}", id))
                .andExpect(status().isNoContent());
        // Verifica que o status é 204 — sem conteúdo na resposta
    }
}
