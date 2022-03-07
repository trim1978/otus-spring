package ru.otus.trim.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.trim.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = "book")
    List<Comment> findAll();

    @EntityGraph(attributePaths = {"book"})
    @Query("select c from Comment c where c.book.id = :book")
    List<Comment> findByBookId(@Param("book") long book);

    @Modifying
    @Query("update Comment c set c.text = :text where c.id = :id")
    void updateTextById(@Param("id") long id, @Param("text") String text);

}
