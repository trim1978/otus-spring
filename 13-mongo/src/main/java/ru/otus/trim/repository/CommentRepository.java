package ru.otus.trim.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.trim.model.BookBrief;
import ru.otus.trim.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentRepositoryCustom {
    List<Comment> findByBook(BookBrief book);
    void deleteByBook(BookBrief book);
}
