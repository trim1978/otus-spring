package ru.otus.trim.service;

import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;

import java.util.List;

public interface LibraryService {
    void setBook (Book book);
    Book removeBookById (long bookID);
    Book getBookById (long bookID);
    List<Book> getBooks ();

    Author getAuthor (String name);
    Author removeAuthor (String name);
    List<Author> getAuthors ();

    List<String> getGenres ();

    List<Comment> getCommentsByBookId (long bookID);
    void addCommentToBookById (long bookID, String text);
    List<Book> getBooksByGenre (String genre);
    List<Book> getBooksByAuthor (String author);
}
