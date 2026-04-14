package com.example.neo.first_biblioteca_project;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Management Library API",
                version = "1.0",
                description = "API para gerenciamento de livros, autores e editoras"
        )
)
@SpringBootApplication
public class FirstBibliotecaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstBibliotecaProjectApplication.class, args);
	}

}
