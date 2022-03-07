package ru.otus.trim.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.trim.dao.AuthorDaoJdbc;
import ru.otus.trim.dao.BookDao;
import ru.otus.trim.dao.BookDaoJdbc;
import ru.otus.trim.dao.GenreDaoJdbc;
import ru.otus.trim.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Books CRUD")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
class BookDaoTests {

    @Autowired
    private BookDao library;

    @DisplayName("insert")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void insertTest() {
        Book book = library.insertBook("Metel", 1, 6);
        assertThat(book.getTitle()).isEqualTo("Metel");
        assertThat(book.getAuthor().getName()).isEqualTo("Pushkin");
        assertThat(book.getGenre().getName()).isEqualTo("drama");
        assertThat(library.findById(book.getId())).isEqualTo(book);
    }

    @DisplayName("update")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void updateTest() {
        Book book = library.insertBook("Metel", 1, 6);
        library.updateById(book.getId(), 4);
        assertThat(library.findById(book.getId()).getGenre().getName()).isEqualTo("lyric");
    }

    @DisplayName("delete")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void deleteTest() {
        Book book = library.insertBook("Metel", 1, 6);
        library.deleteById(book.getId());
        assertThat(library.findById(book.getId())).isNull();
    }

    @DisplayName("select")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void selectTest() {
        Book book = library.insertBook("Metel", 1, 6);
        assertThat(library.getAllBooks()).isNotEmpty();
    }
}
