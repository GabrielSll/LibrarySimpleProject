package com.example.neo.first_biblioteca_project.repository;

import com.example.neo.first_biblioteca_project.model.PublisherModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublisherRepository extends JpaRepository<PublisherModel, UUID> {
    boolean existsByName(String name);
}
