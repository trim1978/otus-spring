package ru.otus.trim.dao;

import ru.otus.trim.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author insertAuthor(String name);

    Author findById(int id);

    boolean deleteById(int id);

    List<Author> getAllAuthors();

}
