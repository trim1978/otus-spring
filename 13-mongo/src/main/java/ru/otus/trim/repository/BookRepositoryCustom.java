package ru.otus.trim.repository;

import ru.otus.trim.model.Comment;

import java.util.List;

public interface BookRepositoryCustom {
    List<Comment> getBookCommentById(String bookId);
}
