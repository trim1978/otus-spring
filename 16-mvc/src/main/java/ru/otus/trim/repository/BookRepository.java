package ru.otus.trim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    //@EntityGraph(attributePaths = {"authors","genres"})
    //@Query("SELECT b FROM books b JOIN FETCH b.authors JOIN FETCH b.genres")
    List<Book> findAll();

    //@EntityGraph(attributePaths = {"authors","genres"})
    List<Book> findByTitle(String s);

    //@Query("select b from Book b JOIN FETCH b.authors a JOIN FETCH b.genres g where b.id = :book and b.id = a.")
    //Optional<Book> findByID(@Param("book") long book);

    List<Book> findAll(Sort sort); // TODO
    Page<Book> findAll(Pageable pageabale); // TODO
}
