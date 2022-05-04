package ru.otus.trim.service;

import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;

import java.util.List;
import java.util.Set;

public interface LibraryService {
    void changeBook(String bookId, String title, String author, List<String> genres);
    void removeBookById (String bookId);
    Book getBookById (String bookId);
    List<Book> getBooks ();
    Book addBook (String title, String author, String genre);

    Author getAuthor (String name);
    Author removeAuthor (String name);
    List<Author> getAuthors ();

    Set<String> getGenres ();

    List<Book> getBooksByGenre (String genre);
    List<Book> getBooksByAuthor (String author);

    Comment addComment (String bookId, String text);
    void changeComment (String commentId, String text);
    void removeComment (String commentId);
    List<Comment> getComments(String bookId);
    Comment getComment(String commentId);
}
