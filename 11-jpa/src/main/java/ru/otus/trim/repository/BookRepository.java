package ru.otus.trim.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.trim.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    //@EntityGraph(attributePaths = {"authors","genres"})
    //@Query("SELECT b FROM books b JOIN FETCH b.authors JOIN FETCH b.genres")
    List<Book> findAll();

    //@EntityGraph(attributePaths = {"authors","genres"})
    List<Book> findByTitle(String s);
}
