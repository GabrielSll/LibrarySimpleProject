package com.example.neo.first_biblioteca_project.mapper;

import com.example.neo.first_biblioteca_project.dto.PublisherRequestDTO;
import com.example.neo.first_biblioteca_project.dto.PublisherResponseDTO;
import com.example.neo.first_biblioteca_project.model.PublisherModel;

import java.util.concurrent.Flow;

public class PublisherMapper {

    public static PublisherResponseDTO toDto(PublisherModel publisher) {
        PublisherResponseDTO dto = new PublisherResponseDTO();
        dto.setId(publisher.getId());
        dto.setName(publisher.getName());
        return dto;
    }

    public static PublisherModel toEntity(PublisherRequestDTO dto) {
        PublisherModel entity = new PublisherModel();
        entity.setName(dto.getName());
        return entity;
    }
}
