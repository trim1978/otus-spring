package ru.otus.trim.repository;

import ru.otus.trim.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAllBooks();

    Book save (Book book);
    void deleteBookById(long bookID);
    Book getBookById(long id);
}
