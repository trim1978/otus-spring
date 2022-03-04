package ru.otus.trim.repository;

import ru.otus.trim.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
//    Author insertAuthor(String name);
//
    Optional<Author> getAuthorById(int id);
//
//    boolean deleteAuthorById(int id);

    Author save (Author author);
    List<Author> getAllAuthors();

}
