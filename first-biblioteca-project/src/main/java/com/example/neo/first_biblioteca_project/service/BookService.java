package com.example.neo.first_biblioteca_project.service;

import com.example.neo.first_biblioteca_project.dto.BookRequestDTO;
import com.example.neo.first_biblioteca_project.dto.BookResponseDTO;
import com.example.neo.first_biblioteca_project.mapper.BookMapper;
import com.example.neo.first_biblioteca_project.model.AuthorModel;
import com.example.neo.first_biblioteca_project.model.BookModel;
import com.example.neo.first_biblioteca_project.model.PublisherModel;
import com.example.neo.first_biblioteca_project.repository.AuthorRepository;
import com.example.neo.first_biblioteca_project.repository.BookRepository;
import com.example.neo.first_biblioteca_project.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
    }

    public BookResponseDTO createBook(BookRequestDTO dto) {

        PublisherModel publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        List<AuthorModel> authors = authorRepository.findAllById(dto.getAuthorIds());

        BookModel book = BookMapper.toEntity(dto, publisher, authors);

        bookRepository.save(book);

        return BookMapper.toDTO(book);
    }

    public BookResponseDTO updateBook(UUID id, BookRequestDTO dto) {
        BookModel book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setName(dto.getName());

        PublisherModel publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not foundS"));

        book.setPublisher(publisher);

        List<AuthorModel> authors = authorRepository.findAllById(dto.getAuthorIds());
        book.setAuthors(authors);

        BookModel updatedBook = bookRepository.save(book);
        return BookMapper.toDTO(updatedBook);
    }

    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");}
        bookRepository.deleteById(id);
    }

    public List<BookResponseDTO> getAllBooks() {
        List<BookModel> books = bookRepository.findAll();
        return books.stream().map(BookMapper::toDTO).toList();
    }

    public BookResponseDTO getBookById(UUID id) {
        BookModel bookModel = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return BookMapper.toDTO(bookModel);
    }
}
