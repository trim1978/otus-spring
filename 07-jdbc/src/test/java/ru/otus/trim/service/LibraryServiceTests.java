package ru.otus.trim.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.trim.dao.AuthorDao;
import ru.otus.trim.dao.BookDao;
import ru.otus.trim.dao.GenreDao;
import ru.otus.trim.domain.Author;
import ru.otus.trim.domain.Book;
import ru.otus.trim.domain.Genre;
import ru.otus.trim.service.LibraryService;
import ru.otus.trim.service.LibraryServiceImpl;

import static org.mockito.Mockito.*;

@DisplayName("Test Books CRUD")
@JdbcTest
@Import(LibraryServiceImpl.class)
@ExtendWith(MockitoExtension.class)
class LibraryServiceTests {

	private static final String AUTHOR_PUSHKIN = "Pushkin";
	private static final String GENRE_DRAMA = "drama";
	private static final String TITLE_1 = "Metel";
	private static final String TITLE_2 = "Anchar";
	@Autowired
	private LibraryService library;

	@MockBean
	private BookDao bookDao;
	@MockBean
	private AuthorDao authorDao;
	@MockBean
	private GenreDao genreDao;

	@DisplayName("read all authors")
	@Test
	void authorsTest() {
		library.getAuthors();
		library.getAuthor("");
		verify(authorDao, times(2)).getAllAuthors();
	}

	@DisplayName("read all genres")
	@Test
	void genresTest() {
		library.getGenres();
		library.getGenre("");
		verify(genreDao, times(2)).findAll();
	}

	@DisplayName("read all books")
	@Test
	void readAllTest() {
		library.getBooks();
		verify(bookDao, times(1)).getAllBooks();
	}

	@DisplayName("update")
	@Test
	void updateTest() {
		library.saveBook(new Book(1, TITLE_1, new Author(0, AUTHOR_PUSHKIN), new Genre(1, "")));
		verify(bookDao, times(1)).updateById(1, 1);
	}

	@DisplayName("delete")
	@Test
	void deleteTest() {
		when(bookDao.findById(1)).thenReturn(new Book(1, TITLE_1, new Author(1, AUTHOR_PUSHKIN), new Genre(1, "")));
		when(bookDao.deleteById(1)).thenReturn(true);
		library.removeBookById(1);
		verify(bookDao, times(1)).findById(1);
		verify(bookDao, times(1)).deleteById(1);
	}

	@DisplayName("insert")
	@Test
	void insertTest() {
		library.saveBook(new Book(0, TITLE_1, new Author(1, AUTHOR_PUSHKIN), new Genre(1, "")));
		verify(bookDao, times(1)).insertBook(TITLE_1, 1, 1);
		verify(genreDao, times(0)).findById(1);
		verify(authorDao, times(0)).findById(1);

		when(authorDao.insertAuthor(AUTHOR_PUSHKIN)).thenReturn(new Author(1, AUTHOR_PUSHKIN));
		library.saveBook(new Book(0, TITLE_1, new Author(0, AUTHOR_PUSHKIN), new Genre(1, "")));
		verify(authorDao, times(1)).insertAuthor(AUTHOR_PUSHKIN);
	}
}
