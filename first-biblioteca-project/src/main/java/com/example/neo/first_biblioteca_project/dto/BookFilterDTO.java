package com.example.neo.first_biblioteca_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookFilterDTO {

    private String name;           // ?name=Outliers
    private String publisherName;  // ?publisherName=Sextante
    private String authorName;     // ?authorName=Malcolm
}
