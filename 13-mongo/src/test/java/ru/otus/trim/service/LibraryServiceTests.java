package ru.otus.trim.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.trim.changelog.DatabaseChangelog;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

@DataMongoTest
@DisplayName("Test Books library")
@Import(LibraryServiceImpl.class)
class LibraryServiceTests {

    private static final String AUTHOR_PUSHKIN = "Pushkin";
    private static final String GENRE_DRAMA = "drama";
    private static final String TITLE_1 = "Metel";
    private static final String TITLE_2 = "Anchar";
    @Autowired
    private LibraryServiceImpl library;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private CommentRepository commentRepository;

    @DisplayName("get all authors")
    @Test
    void authorsTest() {
        library.getAuthors();
        //library.getAuthor("");
        verify(authorRepository, times(1)).findAll();
    }

//	@DisplayName("read all genres")
//	@Test
//	void genresTest() {
//		library.getGenres();
//		library.getGenre("");
//		verify(genreDao, times(2)).getAllGenres();
//	}

    @DisplayName("get all books")
    @Test
    void readAllTest() {
        library.getBooks();
        verify(bookRepository, times(1)).findAll();
    }

//    @DisplayName("get all comments")
//    @Test
//    void readAllComments() {
//        //Author author = new Author(0, AUTHOR_PUSHKIN);
//        Book book = new Book(TITLE_1, "AUTHOR_PUSHKIN", "horror");
//        library.setBook(book);
//        library.getCommentsByBookId(book.getId());
//        verify(commentRepository, times(1)).findByBook(book);
//        //verify(authorRepository, times(3)).save(author);
//    }

//    @DisplayName("insert author")
//    @Test
//    void insertAuthor() {
//        //Author author = new Author();
//        Book book = new Book(TITLE_1, "AUTHOR_PUSHKIN", "horror");
//        library.setBook(book);
//        verify(authorRepository, times(1)).save(book.getAuthor());
//    }

    @DisplayName("update")
    @Test
    void updateTest() {
        Book book = new Book(0, TITLE_1, new Author(0, AUTHOR_PUSHKIN), List.of("horror"));
        library.setBook(book);
        LinkedList<String> l = new LinkedList<> (book.getGenres());
        l.add("lyrics");
        book.setGenres(l);
        library.setBook(book);
        verify(bookRepository, times(2)).save(book);
    }

    @DisplayName("insert")
    @Test
    void insertTest() {
        Book book = new Book(TITLE_2, AUTHOR_PUSHKIN, "lyrics");
        library.setBook(book);
        verify(bookRepository, times(1)).save(book);
    }

//    @DisplayName("delete")
//    @Test
//    void deleteTest() {
//        //Book book = new Book(TITLE_2, AUTHOR_PUSHKIN, "lyrics");
//        //library.setBook(book);
//        //long id = book.getId();
//        library.removeBookById(1);
//        verify(bookRepository, times(1)).delete(any());
//        //verify(bookRepository, times(2)).findById(id);
//    }

//	@DisplayName("delete")
//	@Test
//	void deleteTest() {
//		when(bookRepository.getBookById(1)).thenReturn(new Book(1, TITLE_1, new Author(0, AUTHOR_PUSHKIN), List.of ("horror")));
//		verify(bookRepository, times(1)).delete (any());
//		library.removeBookById(1);
//		verify(bookRepository, times(1)).getBookById(1);
//		verify(bookRepository, times(1)).deleteBookById(1);
//	}
//
//	@DisplayName("insert")
//	@Test
//	void insertTest() {
//		library.setBook(new Book(0, TITLE_1, new Author(1, AUTHOR_PUSHKIN), new Genre(1, "")));
//		verify(bookRepository, times(1)).insertBook(TITLE_1, 1, 1);
//		verify(bookRepository, times(0)).getGenreById(1);
//		verify(authorRepository, times(0)).getAuthorById(1);
//
//		when(authorRepository.insertAuthor(AUTHOR_PUSHKIN)).thenReturn(new Author(1, AUTHOR_PUSHKIN));
//		library.setBook(new Book(0, TITLE_1, new Author(0, AUTHOR_PUSHKIN), new Genre(1, "")));
//		verify(authorRepository, times(1)).insertAuthor(AUTHOR_PUSHKIN);
//	}
}
