package ru.otus.trim.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, Long> {
    List<Book> findByAuthor(Author name); // it works
    List<Book> findByGenres(String genre); // ! работает
}
