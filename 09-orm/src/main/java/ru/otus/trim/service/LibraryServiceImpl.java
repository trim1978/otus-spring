package ru.otus.trim.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.trim.model.Comment;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;
import ru.otus.trim.repository.GenreRepository;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Genre;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
    public final BookRepository books;
    public final AuthorRepository authors;
    public final GenreRepository genres;
    public final CommentRepository comments;

    @Transactional
    @Override
    public void removeBookById(long bookID) {
        comments.removeComments(bookID);
        books.deleteBookById(bookID);
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBookById(long bookID) {
        return books.getBookById(bookID);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooks() {
        return books.getAllBooks();
    }

    @Transactional
    @Override
    public Book addBook(String title, String author, String genre) {
        Book book = new Book(0L, title, getAuthor(author), getGenre(genre));
        books.save(book);
        return book;
    }

    @Transactional
    @Override
    public Author getAuthor(String name) {
        Optional<Author> author = authors.getAllAuthors().stream().filter(t -> t.getName().equalsIgnoreCase(name)).findAny();
        return author.orElseGet(() -> authors.save(new Author(0, name)));
    }

    @Transactional
    @Override
    public Genre getGenre(String name) {
        Optional<Genre> genre = genres.getAllGenres().stream().filter(t -> t.getName().equalsIgnoreCase(name)).findAny();
        return genre.orElseGet(() -> genres.save(new Genre(0, name)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAuthors() {
        return authors.getAllAuthors();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getGenres() {
        return genres.getAllGenres();
    }

    @Transactional
    @Override
    public Comment addComment(long bookID, String text) {
        Book book = books.getBookById(bookID);
        if (book != null) {
            Comment comment = new Comment(text, book);
            comments.add(comment);
            return comment;
        }
        return null;
    }

    @Override
    public Comment changeComment(long commentID, String text) {
        return comments.update(commentID, text);
    }

    @Transactional
    @Override
    public void removeComment(long commentID) {
        //Comment comment = new Comment(commentID, "", null);
        comments.remove(commentID);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getComments(long bookId) {
        return comments.getAllComments(bookId);
    }
}
