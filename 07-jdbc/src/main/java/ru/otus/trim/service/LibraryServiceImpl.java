package ru.otus.trim.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.trim.dao.AuthorDao;
import ru.otus.trim.dao.BookDao;
import ru.otus.trim.dao.GenreDao;
import ru.otus.trim.domain.Author;
import ru.otus.trim.domain.Book;
import ru.otus.trim.domain.Genre;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {
    public final BookDao books;
    public final AuthorDao authors;
    public final GenreDao genres;

    public LibraryServiceImpl(BookDao books, AuthorDao authors, GenreDao genres) {
        this.books = books;
        this.authors = authors;
        this.genres = genres;
    }

    @Transactional
    @Override
    public Book saveBook(Book book) {
        Genre genre = book.getGenre();
        int genreId = genre.getId();
//        if (genreId == 0) {
//            genre = getGenre(genre.getName());
//        }
        if (book.getId() == 0) {
            Author author = book.getAuthor();
            if (author.getId() == 0) {
                author = authors.insertAuthor(author.getName());
            }
            return books.insertBook(book.getTitle(), author.getId(), genreId);
        } else {
            books.updateById(book.getId(), genreId);
        }
        return book;
    }

    @Transactional
    @Override
    public Book removeBookById(long bookID) {
        Book book = books.findById(bookID);
        if (book != null && !books.deleteById(bookID)) book = null;
        return book;
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBookById(long bookID) {
        return books.findById(bookID);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooks() {
        return books.getAllBooks();
    }

    @Transactional
    @Override
    public Author getAuthor(String name) {
        return authors.getAllAuthors().stream().filter(t -> t.getName().equalsIgnoreCase(name)).findAny().orElse(authors.insertAuthor(name));
    }

    @Transactional(readOnly = true)
    @Override
    public Genre getGenre(String name) {
        return genres.findAll().stream().filter(t -> t.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAuthors() {
        return authors.getAllAuthors();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getGenres() {
        return genres.findAll();
    }
}
