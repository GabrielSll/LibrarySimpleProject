package com.example.neo.first_biblioteca_project.service;

import com.example.neo.first_biblioteca_project.dto.AuthorRequestDTO;
import com.example.neo.first_biblioteca_project.dto.AuthorResponseDTO;
import com.example.neo.first_biblioteca_project.exception.ResourceNotFoundException;
import com.example.neo.first_biblioteca_project.model.AuthorModel;
import com.example.neo.first_biblioteca_project.model.BookModel;
import com.example.neo.first_biblioteca_project.repository.AuthorRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void shouldReturnAuthorWhenFound() {
        UUID  id = UUID.randomUUID();

        AuthorModel author = new AuthorModel();
        author.setId(id);
        author.setName("Malcolm Gladwell");

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        AuthorResponseDTO response = authorService.getAuthorById(id);

        // ASSERT - verifica o resultado
        assertNotNull(response);
        assertEquals("Malcolm Gladwell", response.getName());
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        UUID  id = UUID.randomUUID();

        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorById(id));
    }

    @Test
    void shouldCreateAuthorSuccessfully() {
        UUID  id = UUID.randomUUID();

        AuthorRequestDTO dto =  new AuthorRequestDTO("Malcolm Gladwell");

        AuthorModel author = new AuthorModel();
        author.setId(id);
        author.setName("Malcolm Gladwell");

        when(authorRepository.existsByName("Malcolm Gladwell")).thenReturn(false);
        when(authorRepository.save(any(AuthorModel.class))).thenReturn(author);

        //ACT
        AuthorResponseDTO response = authorService.createAuthor(dto);

        // ASSERT
        assertNotNull(response);
        assertEquals("Malcolm Gladwell", response.getName());
        verify(authorRepository, times(1)).save(any(AuthorModel.class));
        // verify → garante que o save foi chamado exatamente 1 vez
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentAuthor() {
        UUID  id = UUID.randomUUID();

        when(authorRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authorService.deleteAuthor(id));
    }
}
