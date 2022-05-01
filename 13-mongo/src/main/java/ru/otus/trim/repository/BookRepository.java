package ru.otus.trim.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;

import java.util.List;
import java.util.Set;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    List<Book> findByAuthor(Author author);
    List<Book> findByGenres(String genre);
    void deleteByAuthor(Author author);

    @Aggregation("{ '$project': { '_id' : '$genres' } }")
    Set<String> findAllGenres();
}
