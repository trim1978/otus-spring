package ru.otus.trim.service;

import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.model.Genre;

import java.util.List;

public interface LibraryService {
    void removeBookById (long bookID);
    Book getBookById (long bookID);
    List<Book> getBooks ();
    Book addBook (String title, String author, String genre);

    Author getAuthor (String name);
    List<Author> getAuthors ();

    Genre getGenre(String name);
    List<Genre> getGenres ();

    Comment addComment (long bookID, String text);
    void changeComment (long commentID, String text);
    void removeComment (long commentID);
    List<Comment> getComments(long bookId);
}
