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
import ru.otus.trim.model.Author;
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

    @Autowired
    private LibraryService library;

    @DisplayName("insert")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void insertTest() {
        Book book = library.addBook("Anchar", "Pushkin", "lyrics");
        assertThat(book.getId()).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Metel");
        assertThat(book.getAuthor().getName()).isEqualTo("Pushkin");
        assertThat(book.getGenres().contains("drama"));
    }

    @DisplayName("insert author")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void insertAuthorTest() {
        Book book = library.addBook("Anchar", "Pushkin", "lyrics");
        assertThat(library.getAuthor("AUTHOR_PUSHKIN")).isNotNull();
    }

    @DisplayName("update")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void updateTest() {
        Book book = library.addBook("Anchar", "Pushkin", "lyrics");
        LinkedList<String> l = new LinkedList<> (book.getGenres());
        l.add("drama");
        book.setGenres(l);
        library.changeBook(book.getId(), "Mciri", "Lermontoc", l);
        assertThat(library.getBookById(book.getId()).getGenres().contains("drama"));
    }

    @DisplayName("delete")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void deleteTest() {
        Book book = library.addBook("Anchar", "Pushkin", "lyrics");
        assertThat(library.getBookById(book.getId())).isNotNull();
        library.removeBookById(book.getId());
        assertThat(library.getBookById(book.getId())).isNull();
    }

    @DisplayName("select")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void selectTest() {
        assertThat(library.getBooks()).isNotEmpty();
        assertThat(library.getGenres()).isNotEmpty();
        assertThat(library.getAuthors()).isNotEmpty();
    }

//    @DisplayName("delete cascade book")
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
//    @Test
//    void deleteCascadeBookTest() {
//        //System.out.println (library.getCommentsByBookId(1));
//        assertThat(library.getCommentsByBookId(1)).isNotEmpty();
//        Book book = library.getBookById(1);
//        //System.out.println (book);
//        library.removeBookById(1);
//        assertThat(library.getCommentsByBookId(1)).isEmpty();
//    }

    @DisplayName("delete cascade author")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void deleteCascadeAuthorTest() {
        assertThat(library.getAuthor("Pushkin")).isNotNull();
        assertThat(library.getBooksByAuthor("Pushkin")).isNotEmpty();
        assertThat(library.removeAuthor("Pushkin")).isNotNull();
        assertThat(library.getAuthor("Pushkin")).isNull();
        assertThat(library.getBooksByAuthor("Pushkin")).isEmpty();
    }
}
