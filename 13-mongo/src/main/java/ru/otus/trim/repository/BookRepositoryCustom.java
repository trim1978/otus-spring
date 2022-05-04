package ru.otus.trim.repository;

import ru.otus.trim.model.Author;

import java.util.List;

public interface BookRepositoryCustom {
    void updateBook(String bookId, String title, Author author, List<String> genres);
}
