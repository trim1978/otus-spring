package ru.otus.trim.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.trim.domain.Author;
import ru.otus.trim.domain.Book;
import ru.otus.trim.domain.Genre;
import ru.otus.trim.service.LibraryService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCommandComponent {

    private final LibraryService library;

    @ShellMethod(value = "Add author", key = "ins_author")
    public Author addAuthor(String name) {
        return library.getAuthor(name);
    }

    @ShellMethod(value = "Add book", key = "ins_book")
    public Book setBookGenre(String title, String author, String genre) {
        return library.saveBook(new Book(0, title, addAuthor(author), library.getGenre(genre)));
    }

    @ShellMethod(value = "Set book genre", key = "set_genre")
    public Book setBookGenre(long bookID, String genre) {
        Book book = library.getBookById(bookID);
        book.setGenre(library.getGenre(genre));
        return library.saveBook(book);
    }

    @ShellMethod(value = "Remove book", key = "remove_book")
    public String removeBook(long id) {
        Book removed = library.removeBookById(id);
        return "book was removed " + removed;
    }

    @ShellMethod(value = "Get book", key = "get_book")
    public Book getBook(long id) {
        return library.getBookById(id);
    }

    @ShellMethod(value = "Get all authors", key = "get_authors")
    public List<Author> getAuthors() {
        return library.getAuthors();
    }

    @ShellMethod(value = "Get all genres", key = "get_genres")
    public List<Genre> getGenres() {
        return library.getGenres();
    }

    @ShellMethod(value = "Get all books", key = "get_books")
    public List<Book> getBooks() {
        return library.getBooks();
    }

}
