package ru.otus.trim.repository;

import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;

import java.util.List;

public interface BookRepositoryCustom {
    List<Comment> getBookCommentById(String bookId);
    void updateBook(String bookId, String title, Author author, List<String> genres);

}
