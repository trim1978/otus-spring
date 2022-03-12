package ru.otus.trim.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.trim.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //@EntityGraph(attributePaths = "book")
    List<Comment> findAll();

    //@EntityGraph(attributePaths = "book")
    List<Comment> findByBookId(long book);
}
