package ru.otus.trim.repository;

import ru.otus.trim.model.Comment;

import java.util.List;

public interface CommentRepository {
    void add(Comment comment);
    void deleteById(long commentID);
    void deleteByBookId(long bookID);
    Comment update (long commentID, String text);
    List<Comment> getAllComments(long bookId);
}
