package com.example.neo.first_biblioteca_project.repository;

import com.example.neo.first_biblioteca_project.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BookRepository extends JpaRepository<BookModel, UUID>,
        JpaSpecificationExecutor<BookModel> {}
