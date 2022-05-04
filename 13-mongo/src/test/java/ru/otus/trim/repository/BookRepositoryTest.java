package ru.otus.trim.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {

    private static Author author = new Author("author");
    private static Book book = new Book("1", "test",
            author,
            List.of("drama"));

    @Autowired
    private BookRepository books;
    @Autowired
    private AuthorRepository authors;

    @BeforeAll
    public static void initDb(@Autowired MongoTemplate db) {
        author = db.insert(author);
        book = db.insert(book);
    }

    @AfterAll
    public static void dropDb(@Autowired MongoTemplate db) {
        db.getDb().drop();
    }

    @Test
    @DisplayName("должен найти все книги с заданным жанром")
    public void shouldFindBooksByGenre() {
        List<Book> actual = books.findByGenres("drama");
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("должен вернуть пустой список книг по не существующему жанру")
    public void shouldReturnEmptyBooksByGenre() {
        List<Book> actual = books.findByGenres("DOESNT_EXIST_GENRE");
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("должен вернуть список книг по заданному автору")
    public void shouldReturnBooksByAuthor() {
        List<Book> actual = books.findByAuthor(author);
        assertThat(actual).isEqualTo(Collections.singletonList(book));
    }

    @Test
    @DisplayName("должен поменять данные книги")
    public void shouldUpdateBooks() {
        books.updateBook("1", "aaa", authors.save(new Author("New Author")), List.of("humor"));
        Book actual = books.findById("1").orElse(null);
        assertThat(actual.getTitle()).isEqualTo("aaa");
        assertThat(actual.getAuthor().getName()).isEqualTo("New Author");
        assertThat(actual.getGenres()).isEqualTo(List.of("humor"));
    }
}
