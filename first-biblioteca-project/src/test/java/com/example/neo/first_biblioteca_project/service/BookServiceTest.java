package com.example.neo.first_biblioteca_project.service;

import com.example.neo.first_biblioteca_project.dto.BookRequestDTO;
import com.example.neo.first_biblioteca_project.dto.BookResponseDTO;
import com.example.neo.first_biblioteca_project.exception.ResourceNotFoundException;
import com.example.neo.first_biblioteca_project.model.AuthorModel;
import com.example.neo.first_biblioteca_project.model.BookModel;
import com.example.neo.first_biblioteca_project.model.PublisherModel;
import com.example.neo.first_biblioteca_project.repository.AuthorRepository;
import com.example.neo.first_biblioteca_project.repository.BookRepository;
import com.example.neo.first_biblioteca_project.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService; // inst real do bookservice e implementa os 3 repo acima

    @Test
    void shouldReturnBookWhenFound() {

        //ARRANGE - preparação dos dados
        UUID id = UUID.randomUUID();

        PublisherModel publisher = new PublisherModel();
        publisher.setName("Sextante");

        AuthorModel author = new AuthorModel();
        author.setName("Malcolm Gladwell");

        BookModel book = new BookModel();
        book.setId(id);
        book.setName("Outliers");
        book.setPublisher(publisher);
        book.setAuthors(List.of(author));

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        BookResponseDTO response = bookService.getBookById(id);

        // ASSERT - verifica o resultado
        assertNotNull(response);
        assertEquals("Outliers", response.getName());
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {

        //ARRANGE
        UUID id = UUID.randomUUID();

        //Test
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(id));
    }

    @Test
    void shouldCreateBookSuccessfully() {

        // ARRANGE
        UUID publisherId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();

        BookRequestDTO dto = new BookRequestDTO("Outliers", publisherId, List.of(authorId));

        PublisherModel publisher = new PublisherModel();
        publisher.setId(publisherId);
        publisher.setName("Sextante");

        AuthorModel author = new AuthorModel();
        author.setId(authorId);
        author.setName("Malcolm Gladwell");

        BookModel savedBook = new BookModel();
        savedBook.setId(UUID.randomUUID());
        savedBook.setName("Outliers");
        savedBook.setPublisher(publisher);
        savedBook.setAuthors(List.of(author));

        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher));
        when(authorRepository.findAllById(List.of(authorId))).thenReturn(List.of(author));
        when(bookRepository.save(any(BookModel.class))).thenReturn(savedBook);

        // ACT
        BookResponseDTO response = bookService.createBook(dto);

        // ASSERT
        assertNotNull(response);
        assertEquals("Outliers", response.getName());
        verify(bookRepository, times(1)).save(any(BookModel.class));
        // verify → garante que o save foi chamado exatamente 1 vez
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentBook() {

        // ARRANGE
        UUID id = UUID.randomUUID();

        when(bookRepository.existsById(id)).thenReturn(false);
        // Simula que o livro não existe

        // ACT & ASSERT
        assertThrows(RuntimeException.class, () -> bookService.deleteBook(id));
    }
}
