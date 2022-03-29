package ru.otus.trim.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.trim.model.Author;

public interface AuthorRepository extends MongoRepository<Author, Integer> {
    Author findByName(String name); // самодостаточный метод, который не нужно реализовавыть
}
