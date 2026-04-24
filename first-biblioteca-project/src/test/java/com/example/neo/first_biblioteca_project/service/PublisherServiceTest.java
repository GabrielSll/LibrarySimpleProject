package com.example.neo.first_biblioteca_project.service;

import com.example.neo.first_biblioteca_project.dto.PublisherRequestDTO;
import com.example.neo.first_biblioteca_project.dto.PublisherResponseDTO;
import com.example.neo.first_biblioteca_project.exception.ResourceNotFoundException;
import com.example.neo.first_biblioteca_project.model.PublisherModel;
import com.example.neo.first_biblioteca_project.repository.PublisherRepository;
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
public class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    @Test
    void shouldReturnPublisherWhenFound() {
        UUID id = UUID.randomUUID();

        PublisherModel publisher = new PublisherModel();
        publisher.setId(id);
        publisher.setName("Sextante");

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        PublisherResponseDTO response = publisherService.getPublisherById(id);

        assertNotNull(response);
        assertEquals("Sextante", response.getName());
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();

        when(publisherRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> publisherService.getPublisherById(id));
    }

    @Test
    void shouldCreateAuthorSuccessfully() {
        UUID id = UUID.randomUUID();

        PublisherRequestDTO dto = new PublisherRequestDTO("Sextante");

        PublisherModel publisher = new PublisherModel();
        publisher.setId(id);
        publisher.setName("Sextante");

        when(publisherRepository.existsByName("Sextante")).thenReturn(false);
        when(publisherRepository.save(any(PublisherModel.class))).thenReturn(publisher);

        PublisherResponseDTO response = publisherService.createPublisher(dto);

        assertNotNull(response);
        assertEquals("Sextante", response.getName());
        verify(publisherRepository, times(1)).save(any(PublisherModel.class));
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentAuthor() {
        UUID id =  UUID.randomUUID();

        when(publisherRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> publisherService.deletePublisher(id));
    }
}
