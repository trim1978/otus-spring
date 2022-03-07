package ru.otus.trim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.trim.model.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    //@Query("select g from Genre g where g.name = :name")
    Optional<Genre> findByName(String name);

//    @Modifying
//    //@Transactional
//    @Query("update Genre g set g.name = :name where g.id = :id")
//    void updateEmailById(@Param("id") int id, @Param("name") String name);
}
