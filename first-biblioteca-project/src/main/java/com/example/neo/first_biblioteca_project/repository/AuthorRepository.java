package com.example.neo.first_biblioteca_project.repository;

import com.example.neo.first_biblioteca_project.model.AuthorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<AuthorModel, UUID> {
    boolean existsByName(String name);
}
