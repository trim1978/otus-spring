package ru.otus.trim.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Custom Repository")
@DataMongoTest
public class CustomRepositoryTest {
    @Autowired
    private MongoTemplate template;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    @BeforeAll
    public static void initDB(@Autowired MongoTemplate template) {
        Author author1 = template.insert(new Author(1, "author1"));
        Author author2 = template.insert(new Author(2, "author2"));
        template.insert(new Book(1, "book1", author1, List.of("genre")));
        template.insert(new Book(2, "book2", author2, List.of("genre")));
    }


    @Test
    void shouldFindCorrectAuthorByName() {
        Author expected = authorRepository.findById(1).orElse(null);
        Author actual = authorRepository.findByName("author1");
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldFindCorrectBooksByAuthorName() {
        Book expected = bookRepository.findById(1L).orElse(null);
        List<Book> actual = bookRepository.findByAuthor(new Author(1, "author1"));
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getId()).isEqualTo(expected.getId());
        assertThat(actual.get(0).getAuthor()).isEqualTo(expected.getAuthor());
    }

    @Test
    void shouldFindCorrectBooksByGenreName() {
        List<Book> actual = bookRepository.findByGenres("genre");
        assertThat(actual).isNotNull()
                .hasSize(2);
    }

    @Test
    void shouldFindCorrectCommentForBook() {
        Book book = bookRepository.findById(1L).orElse(null);
        List<Comment> actual = commentRepository.findByBook(book);
        assertThat(actual).isNotNull().isEmpty();
    }


}
