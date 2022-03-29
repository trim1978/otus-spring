package ru.otus.trim.service;

import org.springframework.stereotype.Service;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;

@Service
public class LibraryOutServiceImpl implements LibraryOutService {
    @Override
    public String getAuthorString(Author author) {
        return String.format ("A: id=%d -> '%s'",author.getId(), author.getName());
    }

    @Override
    public String getGenreString(String genre) {
        return String.format ("G: '%s'", genre);
    }

    @Override
    public String getBookString(Book book) {
        return String.format ("B: id=%d -> '%s' %s (%s)", book.getId(), book.getTitle(), getAuthorString(book.getAuthor()), getGenreString(book.getGenres().get(0)));
    }

    @Override
    public String getCommentString(Comment comment) {
        return String.format ("C: id=%d '%s' %s -> B: id=%d -> '%s'",comment.getId(), comment.getText(), comment.getDatetime(), comment.getBook().getId(), comment.getBook().getTitle());
    }
}
