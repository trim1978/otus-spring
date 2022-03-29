package ru.otus.trim.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.trim.model.Book;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Books CRUD")
@ComponentScan(basePackages = "ru.otus.trim")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Import(LibraryServiceImpl.class)
class BookCRUDTests {

    private static final String AUTHOR_PUSHKIN = "Pushkin";
    private static final String GENRE_DRAMA = "drama";
    private static final String TITLE_1 = "Metel";
    @Autowired
    private LibraryService library;

    @DisplayName("insert")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void insertTest() {
        Book book = new Book (TITLE_1, AUTHOR_PUSHKIN, GENRE_DRAMA);
        library.setBook(book);
        assertThat(book.getId()).isGreaterThan(0);
        assertThat(book.getTitle()).isEqualTo(TITLE_1);
        assertThat(book.getAuthor().getName()).isEqualTo(AUTHOR_PUSHKIN);
        assertThat(book.getGenres().contains(GENRE_DRAMA));
    }

    @DisplayName("insert author")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void insertAuthorTest() {
        Book book = new Book (TITLE_1, "AUTHOR_PUSHKIN", GENRE_DRAMA);
        library.setBook(book);
        assertThat(library.getAuthor("AUTHOR_PUSHKIN")).isNotNull();
    }

    @DisplayName("update")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void updateTest() {
        Book book = new Book (TITLE_1, AUTHOR_PUSHKIN, GENRE_DRAMA);
        library.setBook(book);
        LinkedList<String> l = new LinkedList<> (book.getGenres());
        l.add("lyric");
        book.setGenres(l);
        library.setBook(book);
        assertThat(library.getBookById(book.getId()).getGenres().contains("lyric"));
    }

    @DisplayName("delete")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void deleteTest() {
        Book book = new Book (TITLE_1, AUTHOR_PUSHKIN, GENRE_DRAMA);
        library.setBook(book);
        assertThat(library.getBookById(book.getId())).isNotNull();
        library.removeBookById(book.getId());
        assertThat(library.getBookById(book.getId())).isNull();
    }

    @DisplayName("select")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void selectTest() {
        //Book book = new Book (TITLE_1, AUTHOR_PUSHKIN, GENRE_DRAMA);
        //assertThat(library.getBooks()).isEmpty();
        //System.out.println (library.getBooks());

        //library.setBook(book);
        assertThat(library.getBooks()).isNotEmpty();
        assertThat(library.getGenres()).isNotEmpty();
        assertThat(library.getAuthors()).isNotEmpty();
        //assertThat(library.getCommentsByBookId()Books()).isNotEmpty();
    }

    @DisplayName("delete cascade book")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void deleteCascadeBookTest() {
        //System.out.println (library.getCommentsByBookId(1));
        assertThat(library.getCommentsByBookId(1)).isNotEmpty();
        Book book = library.getBookById(1);
        //System.out.println (book);
        library.removeBookById(1);
        assertThat(library.getCommentsByBookId(1)).isEmpty();
    }

    @DisplayName("delete cascade author")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void deleteCascadeAuthorTest() {
        assertThat(library.getAuthor(AUTHOR_PUSHKIN)).isNotNull();
        assertThat(library.getBooksByAuthor(AUTHOR_PUSHKIN)).isNotEmpty();
        assertThat(library.removeAuthor(AUTHOR_PUSHKIN)).isNotNull();
        assertThat(library.getAuthor(AUTHOR_PUSHKIN)).isNull();
        assertThat(library.getBooksByAuthor(AUTHOR_PUSHKIN)).isEmpty();
    }
}
