package com.example.neo.first_biblioteca_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100)
    private String name;

    @ManyToMany(mappedBy = "authors")
    private List<BookModel> books;
}
