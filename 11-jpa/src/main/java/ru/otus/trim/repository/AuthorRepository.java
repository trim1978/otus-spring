package ru.otus.trim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.trim.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findAll();
    Optional<Author> findByName(String s);
}
