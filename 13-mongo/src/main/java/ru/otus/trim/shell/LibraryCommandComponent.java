package ru.otus.trim.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.service.LibraryService;

import java.util.List;

@ShellComponent
public class LibraryCommandComponent {

    private final LibraryService library;

    public LibraryCommandComponent(LibraryService library) {
        this.library = library;
    }

    @ShellMethod(value = "Add author", key = {"ins_author","ia","aa"})
    public Author addAuthor(String name) {
        return library.getAuthor(name);
    }

//    @ShellMethod(value = "Add book", key = "ins_book")
//    public Book addBook(String title, String author, String genre) {
//        return library.setBook(new Book(0, title, addAuthor(author), library.getGenre(genre)));
//    }
//
//    @ShellMethod(value = "Set book genre", key = "set_genre")
//    public Book addBook(long bookID, String genre) {
//        Book book = library.getBookById(bookID);
//        book.setGenre(library.getGenre(genre));
//        return library.setBook(book);
//    }

    @ShellMethod(value = "Remove book", key = {"remove_book","rb","db"})
    public String removeBook(long id) {
        Book removed = library.removeBookById(id);
        return "book was removed " + removed;
    }

    @ShellMethod(value = "Get book", key = {"get_book", "gb"})
    public Book getBook(long id) {
        return library.getBookById(id);
    }

    @ShellMethod(value = "Get all authors", key = {"get_authors", "gaa"})
    public List<Author> getAuthors() {
        return library.getAuthors();
    }

    @ShellMethod(value = "Get all genres", key = {"get_genres","gag"})
    public List<String> getGenres() {
        return library.getGenres();
    }

    @ShellMethod(value = "Get all books", key = {"get_books","gab"})
    public List<Book> getBooks() {
        return library.getBooks();
    }


    @ShellMethod(value = "Get books by genre", key = {"get_genre","gg"})
    public List<Book> getBooksByGenre(String genre) {
        return library.getBooksByGenre(genre);
    }
    @ShellMethod(value = "Get books by author", key = {"get_author","ga"})
    public List<Book> getBooksByAuthor(String author) {
        return library.getBooksByAuthor(author);
    }


}
