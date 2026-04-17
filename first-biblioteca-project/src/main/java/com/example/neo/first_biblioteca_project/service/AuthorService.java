package com.example.neo.first_biblioteca_project.service;

import com.example.neo.first_biblioteca_project.dto.AuthorFilterDTO;
import com.example.neo.first_biblioteca_project.dto.AuthorRequestDTO;
import com.example.neo.first_biblioteca_project.dto.AuthorResponseDTO;
import com.example.neo.first_biblioteca_project.exception.ResourceAlreadyExistsException;
import com.example.neo.first_biblioteca_project.exception.ResourceNotFoundException;
import com.example.neo.first_biblioteca_project.mapper.AuthorMapper;
import com.example.neo.first_biblioteca_project.model.AuthorModel;
import com.example.neo.first_biblioteca_project.repository.AuthorRepository;
import com.example.neo.first_biblioteca_project.specification.AuthorSpecificiation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {
        if (authorRepository.existsByName(dto.getName())) {
            throw new ResourceAlreadyExistsException(
                    "There is already an author with that name: " + dto.getName()
            );
        }
        AuthorModel author = AuthorMapper.toEntity(dto);
        authorRepository.save(author);
        return AuthorMapper.toDTO(author);
    }

    public AuthorResponseDTO updateAuthor(UUID id, AuthorRequestDTO dto) {
        AuthorModel author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.setName(dto.getName());
        AuthorModel authorUpdated = authorRepository.save(author);
        return AuthorMapper.toDTO(authorUpdated);
    }

    public void deleteAuthor(UUID id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found");}
        authorRepository.deleteById(id);
    }

    public Page<AuthorResponseDTO> getAllAuthors(AuthorFilterDTO filter, Pageable pageable) {

        Specification<AuthorModel> spec = (root, query, cb) ->
                cb.conjunction();

        if (filter.getName() != null && !filter.getName().isBlank()) {
            spec = spec.and(AuthorSpecificiation.nameContains(filter.getName()));
        }

       Page<AuthorModel> authors = authorRepository.findAll(spec, pageable);
       return authors.map(AuthorMapper::toDTO);
    }

    public AuthorResponseDTO getAuthorById(UUID id) {
        AuthorModel authorModel = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with the id: " + id));

        return AuthorMapper.toDTO(authorModel);
    }
}
