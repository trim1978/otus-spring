package ru.otus.trim.repository;

import ru.otus.trim.model.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    void updateComment(String commentId, String text);
}
