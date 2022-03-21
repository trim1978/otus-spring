package ru.otus.trim.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Genre;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;
import ru.otus.trim.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис библиотеки должен")
@SpringBootTest
class LibraryServiceTest {

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
        Book book_ = new Book();
        given(books.findById(1L)).willReturn(Optional.of(book_));
        Book book = library.getBookById(1);
        assertThat(book).isNotNull().isEqualTo(book_);
    }

    @DisplayName("выдавать ошибку на запрос книгу по неверному id")
    @Test
    void shouldNotFindBookById() {
        Book book = library.getBookById(1);
        assertThat(book).isNull();
    }

    @DisplayName("удалять книгу по её id")
    @Test
    void shouldRemoveBookById() {
        //given(comments.deleteByBookId(1L)).;
        assertThat(library.getBookById(1)).isNotNull();
        library.removeBookById(1);
        assertThat(library.getBookById(1)).isNull();
        assertThat(library.getComments(1)).hasSize(0);
    }

    @DisplayName("добавлять книгу")
    @Test
    void shouldAddBookById() {
        Author author = new Author ();
        Genre genre = new Genre ();
        Book book_ = new Book("", author, genre);
        given(authors.findByName(any())).willReturn(Optional.of(author));
        given(genres.findByName(any())).willReturn(Optional.of(genre));
        given(books.save(any())).willReturn(book_);
        Book book = library.addBook("", author.getName(), genre.getName());
        assertThat(book).isEqualTo(book_);
    }
}