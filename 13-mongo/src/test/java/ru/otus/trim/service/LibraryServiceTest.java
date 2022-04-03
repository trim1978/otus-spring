package ru.otus.trim.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис библиотеки должен")
@SpringBootTest
@Import(LibraryServiceImpl.class)
class LibraryServiceTest {

    @Autowired
    private LibraryServiceImpl library;

    @MockBean
    private BookRepository books;
    @MockBean
    private AuthorRepository authors;

    @DisplayName("возвращать список всех книг")
    @Test
    void shouldFindAllBooks() {
        List<Book> list = List.of(new Book(), new Book());
        given(books.findAll()).willReturn(list);
        List<Book> books = library.getBooks();
        assertThat(books).hasSize(2).containsExactlyElementsOf(list);
    }
    @DisplayName("добавлять книгу")
    @Test
    void shouldAddBook() {
        Book book_ = new Book();
        given(books.save(any())).willReturn(book_);
        given(authors.findByName(any ())).willReturn(new Author("2"));
        Book book = library.addBook("1", "2", "3");
        assertThat(book).isNotNull();
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
        given(books.findById("1")).willReturn(Optional.of(new Book()));
        library.removeBookById("1");
        verify(books, times(1)).delete(any());
    }

    @DisplayName("менять данные у книги по её id")
    @Test
    void shouldChangeBookById() {
        given(books.findById("1")).willReturn(Optional.of(new Book()));
        library.changeBook("1","1","1",List.of("1"));
        verify(authors, times(1)).findByName(any());
        verify(books, times(1)).save(any());
    }
}