package com.example.neo.first_biblioteca_project.mapper;

import com.example.neo.first_biblioteca_project.dto.BookRequestDTO;
import com.example.neo.first_biblioteca_project.dto.BookResponseDTO;
import com.example.neo.first_biblioteca_project.model.AuthorModel;
import com.example.neo.first_biblioteca_project.model.BookModel;
import com.example.neo.first_biblioteca_project.model.PublisherModel;

import java.util.List;

public class BookMapper {

    public static BookResponseDTO toDTO(BookModel book) {
        BookResponseDTO dto = new BookResponseDTO();

        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setPublisherName(book.getPublisher().getName());
        dto.setAuthors(book.getAuthors().stream().map(AuthorModel::getName).toList());

        return dto;
    }

    public static BookModel toEntity(BookRequestDTO dto, PublisherModel publisher, List<AuthorModel> authors) {
        BookModel entity = new BookModel();

        entity.setName(dto.getName());
        entity.setPublisher(publisher);
        entity.setAuthors(authors);

        return entity;
    }
}
