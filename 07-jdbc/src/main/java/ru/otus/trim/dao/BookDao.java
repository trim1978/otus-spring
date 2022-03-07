package ru.otus.trim.dao;

import ru.otus.trim.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAllBooks();

    boolean deleteById(long id);

    void updateById(long id, int genre);

    Book insertBook(String title, int authorId, int genreId);

    Book findById(long id);
}
