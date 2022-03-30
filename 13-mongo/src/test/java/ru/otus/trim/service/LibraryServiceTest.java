package ru.otus.trim.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.trim.model.Book;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataMongoTest
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
        given(books.findById("1")).willReturn(Optional.of(book_));
        Book book = library.getBookById("1");
        assertThat(book).isNotNull().isEqualTo(book_);
    }

    @DisplayName("выдавать ошибку на запрос книгу по неверному id")
    @Test
    void shouldNotFindBookById() {
        Book book = library.getBookById("1");
        assertThat(book).isNull();
    }

    @DisplayName("удалять книгу по её id")
    @Test
    void shouldRemoveBookById() {
        library.removeBookById("1");
        verify(comments, times(1)).deleteByBook(any());
        verify(books, times(1)).deleteById(any());
    }
}