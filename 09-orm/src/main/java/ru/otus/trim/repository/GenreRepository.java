package ru.otus.trim.repository;

import ru.otus.trim.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> getGenreById(int id);
    Genre save (Genre genre);
    List<Genre> getAllGenres();
}
