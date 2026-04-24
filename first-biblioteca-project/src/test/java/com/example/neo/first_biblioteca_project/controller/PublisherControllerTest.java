package com.example.neo.first_biblioteca_project.controller;

import com.example.neo.first_biblioteca_project.dto.PublisherRequestDTO;
import com.example.neo.first_biblioteca_project.dto.PublisherResponseDTO;
import com.example.neo.first_biblioteca_project.exception.ResourceNotFoundException;
import com.example.neo.first_biblioteca_project.service.PublisherService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PublisherService publisherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnPublisherWhenFound() throws Exception {
        UUID id = UUID.randomUUID();

        PublisherResponseDTO response = new PublisherResponseDTO(id, "Sextante");

        when(publisherService.getPublisherById(id)).thenReturn(response);

        mockMvc.perform(get("/publishers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sextante"));
    }

    @Test
    void shouldReturn404WhenNotFound()  throws Exception {
        UUID id = UUID.randomUUID();

        when(publisherService.getPublisherById(id))
                .thenThrow(new ResourceNotFoundException("Publisher Not Found"));

        mockMvc.perform(get("/publishers/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreatePublisherReturn201()  throws Exception {
        PublisherRequestDTO request = new PublisherRequestDTO("Sextante");

        PublisherResponseDTO response = new PublisherResponseDTO(UUID.randomUUID(), "Sextante");

        when(publisherService.createPublisher(any(PublisherRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sextante"));
    }

    @Test
    void shouldReturn400WhenNameIsBlank()  throws Exception {

        PublisherRequestDTO invalidRequest = new PublisherRequestDTO("");

        mockMvc.perform(post("/publishers").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeletePublisherReturn204()  throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(publisherService).deletePublisher(id);

        mockMvc.perform(delete("/publishers/{id}", id)).andExpect(status().isNoContent());
    }
}
