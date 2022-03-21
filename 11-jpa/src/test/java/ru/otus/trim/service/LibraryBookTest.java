package ru.otus.trim.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Genre;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;
import ru.otus.trim.repository.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Репозиторий Book должен")
@SpringBootTest
class LibraryBookTest {

    @Autowired
    private LibraryServiceImpl library;

    @MockBean
    private BookRepository books;
    @MockBean
    private AuthorRepository authors;
    @MockBean
    private GenreRepository genres;
    @MockBean
    private CommentRepository comments;

    @DisplayName("возвращать список всех книг")
    @Test
    void shouldFindAllBooks() {
        List<Book> list = List.of(new Book(), new Book());
        given(books.findAll()).willReturn(list);
        List<Book> books = library.getBooks();
        assertThat(books).hasSize(2).containsExactlyElementsOf(list);
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
        assertThat(library.getBookById(1)).isNotNull();
        library.removeBookById(1);
        assertThat(library.getBookById(1)).isNull();
        assertThat(library.getComments(1)).hasSize(0);
    }

    @DisplayName("добавлять книгу")
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