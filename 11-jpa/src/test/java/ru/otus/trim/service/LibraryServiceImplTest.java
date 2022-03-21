package ru.otus.trim.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Сервис библиотеки должен")
@DataJpaTest
@Import(LibraryServiceImpl.class)
class LibraryServiceImplTest {

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

    //////////////////////////////////////////////////////////////////

    @DisplayName("возвращать список всех комментариев к книгам")
    @Test
    void shouldFindAllComments() {
        assertThat(library.getComments(1)).hasSize(2);
        assertThat(library.getComments(2)).hasSize(4);
    }

    @DisplayName("добавлять комментарий к книге")
    @Test
    void shouldAddComment() {
        Comment comment = library.addComment(1, "comment");
        assertThat(comment).matches(t -> t.getText().equalsIgnoreCase("comment"))
                .matches(t -> t.getDatetime().getTime() > 0L)
                .matches(t -> t.getId() > 0);
        assertThat(comment).isIn(library.getComments(1));
    }

    @DisplayName("корректировать комментарий к книге")
    @Test
    void shouldSetComment() {
        Comment comment = library.addComment(1, "comment");
        comment = library.changeComment(comment.getId(),"changed");
        assertThat(comment).matches(t -> t.getText().equalsIgnoreCase("changed"))
                .matches(t -> t.getDatetime().getTime() > 0L)
                .matches(t -> t.getId() > 0);
        assertThat(comment).isIn(library.getComments(1));
        //assertThat(library.changeComment(100,"changed")).isNull();
    }

    @DisplayName("удалять комментарий к книге")
    @Test
    void shouldDelComment() {
        Comment comment = library.addComment(1, "comment");
        assertThat(comment).isIn(library.getComments(1));
        library.removeComment(comment.getId());
        assertThat(comment).isNotIn(library.getComments(1));
    }

}