package com.example.neo.first_biblioteca_project.mapper;

import com.example.neo.first_biblioteca_project.dto.AuthorRequestDTO;
import com.example.neo.first_biblioteca_project.dto.AuthorResponseDTO;
import com.example.neo.first_biblioteca_project.model.AuthorModel;

public class AuthorMapper {

    public static AuthorResponseDTO toDTO(AuthorModel author) {
        AuthorResponseDTO dto = new AuthorResponseDTO();

        dto.setId(author.getId());
        dto.setName(author.getName());

        return dto;
    }

    public static AuthorModel toEntity(AuthorRequestDTO authorRequestDTO) {
        AuthorModel entity = new AuthorModel();

        entity.setName(authorRequestDTO.getName());

        return entity;
    }
}
