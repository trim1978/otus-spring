package ru.otus.trim.repository;

import ru.otus.trim.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    Book save (Book book);
    void deleteById(long bookID);
    Optional<Book> findById(long id);
}
