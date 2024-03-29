package ru.otus.trim.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    List<Book> findByAuthor(Author author);
    List<Book> findByGenres(String genre);
    void deleteByAuthor(Author author);
}
