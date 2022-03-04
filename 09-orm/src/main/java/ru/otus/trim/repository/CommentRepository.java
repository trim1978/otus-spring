package ru.otus.trim.repository;

import ru.otus.trim.model.Comment;

import java.util.List;

public interface CommentRepository {
    void add(Comment comment);
    void remove (long commentID);
    Comment update (long commentID, String text);
    List<Comment> getAllComments(long bookId);
    void removeComments(long bookId);
}
