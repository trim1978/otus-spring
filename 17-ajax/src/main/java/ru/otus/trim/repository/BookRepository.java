package ru.otus.trim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.trim.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAll();
}
