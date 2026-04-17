package com.example.neo.first_biblioteca_project.specification;

import com.example.neo.first_biblioteca_project.model.AuthorModel;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecificiation {

    public static Specification<AuthorModel> nameContains(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
