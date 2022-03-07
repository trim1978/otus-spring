package ru.otus.trim.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.trim.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author","genre"})
    List<Book> findAll();

    @EntityGraph(attributePaths = {"author","genre"})
    List<Book> findByTitle(String s);
}
