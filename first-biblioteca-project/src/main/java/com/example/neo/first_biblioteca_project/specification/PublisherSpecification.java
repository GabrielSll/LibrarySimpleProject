package com.example.neo.first_biblioteca_project.specification;

import com.example.neo.first_biblioteca_project.model.PublisherModel;
import org.springframework.data.jpa.domain.Specification;

public class PublisherSpecification {

    public static Specification<PublisherModel> nameContains(String publisher) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), "%" + publisher.toLowerCase() + "%");
    }
}
