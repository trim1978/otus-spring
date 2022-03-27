package ru.otus.trim.service;

import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.model.Genre;

public interface LibraryOutService {
    String getAuthorString (Author author);
    String getGenreString (Genre genre);
    String getBookString (Book book);
    String getCommentString (Comment comment);
}
