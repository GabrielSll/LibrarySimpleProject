package com.example.neo.first_biblioteca_project.specification;

import com.example.neo.first_biblioteca_project.model.BookModel;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<BookModel> nameContains(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<BookModel> publisherContains(String publisher) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("publisher").get("name")),
                        "%" + publisher.toLowerCase() + "%");
    }

    public static Specification<BookModel> authorContains(String author) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("author")),
                    author.toLowerCase() + "%");
        };
    }
}
