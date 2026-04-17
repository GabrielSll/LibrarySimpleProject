package com.example.neo.first_biblioteca_project.service;

import com.example.neo.first_biblioteca_project.dto.BookFilterDTO;
import com.example.neo.first_biblioteca_project.dto.BookRequestDTO;
import com.example.neo.first_biblioteca_project.dto.BookResponseDTO;
import com.example.neo.first_biblioteca_project.exception.ResourceNotFoundException;
import com.example.neo.first_biblioteca_project.mapper.BookMapper;
import com.example.neo.first_biblioteca_project.model.AuthorModel;
import com.example.neo.first_biblioteca_project.model.BookModel;
import com.example.neo.first_biblioteca_project.model.PublisherModel;
import com.example.neo.first_biblioteca_project.repository.AuthorRepository;
import com.example.neo.first_biblioteca_project.repository.BookRepository;
import com.example.neo.first_biblioteca_project.repository.PublisherRepository;
import com.example.neo.first_biblioteca_project.specification.BookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
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

    public Page<BookResponseDTO> getAllBooks(BookFilterDTO filter, Pageable pageable) {

        List<Specification<BookModel>> specs = new ArrayList<>(); ;

        if (filter.getName() != null && !filter.getName().isBlank()) {
            specs.add(BookSpecification.nameContains(filter.getName()));
        }

        if (filter.getPublisherName() != null && !filter.getPublisherName().isBlank()) {
            specs.add(BookSpecification.publisherContains(filter.getPublisherName()));
        }

        if (filter.getAuthorName() != null && !filter.getAuthorName().isBlank()) {
            specs.add(BookSpecification.authorContains(filter.getAuthorName()));
        }

        Specification<BookModel> spec = specs
                .stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());

        Page<BookModel> books = bookRepository.findAll(spec, pageable);
        return books.map(BookMapper::toDTO);
    }

    public BookResponseDTO getBookById(UUID id) {
        BookModel bookModel = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with the id" + id));

        return BookMapper.toDTO(bookModel);
    }
}
