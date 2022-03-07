package ru.otus.trim.dao;

import ru.otus.trim.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre findById(int id);

    List<Genre> findAll();
}
