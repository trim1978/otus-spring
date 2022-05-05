package ru.otus.trim.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;

import java.util.List;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void updateBook(String bookId, String title, Author author, List<String> genres) {
        Query query = new Query(new Criteria("id").is(bookId));
        Update update = new Update();
        update.set("title", title);
        update.set("author", author);
        update.set("genres", genres);
        mongoTemplate.updateFirst(query, update, Book.class);
    }
}
