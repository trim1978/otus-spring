package ru.otus.trim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.trim.model.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Optional<Genre> findByName(String name);
}
