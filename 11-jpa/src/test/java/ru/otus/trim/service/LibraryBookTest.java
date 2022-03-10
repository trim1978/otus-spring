package ru.otus.trim.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Genre;
import ru.otus.trim.service.LibraryServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Репозиторий Book должен")
@ComponentScan("ru.otus.trim")
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(LibraryServiceImpl.class)
class LibraryBookTest {

    @Autowired
    private LibraryServiceImpl library;

    @DisplayName("возвращать список всех книг")
    @Test
    void shouldFindAllBooks() {
        List<Book> books = library.getBooks();
        assertThat(books).hasSize(2);
    }

    @DisplayName("возвращать книгу по её id")
    @Test
    void shouldFindBookById() {
        Book book = library.getBookById(1);
        assertThat(book)
                .hasFieldOrPropertyWithValue("title", "Metel");
    }

    @DisplayName("удалять книгу по её id")
    @Test
    void shouldRemoveBookById() {
        library.removeBookById(1);
        assertThat(library.getBookById(1)).isNull();
        assertThat(library.getComments(1)).hasSize(0);
    }

    @DisplayName("добалять книгу")
    @Test
    void shouldAddBookById() {
        Book book = library.addBook("Na dne", "Gorky", "drama");
        Genre genre = library.getGenre("drama");
        Author author = library.getAuthor("Gorky");
        assertThat(book).matches(t -> t.getTitle().equalsIgnoreCase("Na dne"))
                .matches(t -> t.getGenres().contains(genre))
                .matches(t -> t.getId() > 0)
            .matches(t -> t.getAuthors().contains(author));
        assertThat(book).isIn(library.getBooks());
    }
}