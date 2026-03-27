package com.example.neo.first_biblioteca_project.service;

import com.example.neo.first_biblioteca_project.dto.PublisherRequestDTO;
import com.example.neo.first_biblioteca_project.dto.PublisherResponseDTO;
import com.example.neo.first_biblioteca_project.mapper.PublisherMapper;
import com.example.neo.first_biblioteca_project.model.PublisherModel;
import com.example.neo.first_biblioteca_project.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public PublisherResponseDTO createPublisher(PublisherRequestDTO dto) {
        PublisherModel publisher = PublisherMapper.toEntity(dto);

        publisherRepository.save(publisher);

        return PublisherMapper.toDto(publisher);
    }

    public PublisherResponseDTO updatePublisher(UUID id, PublisherRequestDTO dto) {
        PublisherModel publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        publisher.setName(dto.getName());
        PublisherModel publisherUpdated = publisherRepository.save(publisher);
        return PublisherMapper.toDto(publisherUpdated);
    }

    public void deletePublisher(UUID id) {
        if (!publisherRepository.existsById(id)) {
            throw new RuntimeException("Author not found");}
        publisherRepository.deleteById(id);
    }

    public List<PublisherResponseDTO> getAllPublishers() {
        List<PublisherModel> publishers = publisherRepository.findAll();
        return publishers.stream().map(PublisherMapper::toDto).toList();
    }

    public PublisherResponseDTO getPublisherById(UUID id) {
        PublisherModel publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        return PublisherMapper.toDto(publisher);
    }
}
