package ru.otus.trim.service;

import org.springframework.stereotype.Service;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.model.Genre;

@Service
public class LibraryOutServiceImpl implements LibraryOutService{
    @Override
    public String getAuthorString(Author author) {
        return String.format ("A: id=%d -> '%s'",author.getId(), author.getName());
    }

    @Override
    public String getGenreString(Genre genre) {
        return String.format ("G: id=%d -> '%s'",genre.getId(), genre.getName());
    }

    @Override
    public String getBookString(Book book) {
        return String.format ("B: id=%d -> '%s' %s (%s)", book.getId(), book.getTitle(), getAuthorString(book.getAuthors().get(0)), getGenreString(book.getGenres().get(0)));
    }

    @Override
    public String getCommentString(Comment comment) {
        return String.format ("C: id=%d '%s' %s",comment.getId(), comment.getText(), comment.getDatetime());
    }
}
