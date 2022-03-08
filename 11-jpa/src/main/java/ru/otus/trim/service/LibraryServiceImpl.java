package ru.otus.trim.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.model.Genre;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;
import ru.otus.trim.repository.GenreRepository;

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
        comments.deleteById(bookID);
        books.deleteById(bookID);
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBookById(long bookID) {
        return books.findById(bookID).get();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooks() {
        return books.findAll();
    }

    @Transactional
    @Override
    public Book addBook(String title, String author, String genre) {
        Book book = new Book(title, getAuthor(author), getGenre(genre));
        books.save(book);
        return book;
    }

    @Transactional
    @Override
    public Author getAuthor(String name) {
        Optional<Author> author = authors.findByName(name);
        return author.orElseGet(() -> authors.save(new Author(name)));
    }

    @Transactional
    @Override
    public Genre getGenre(String name) {
        Optional<Genre> genre = genres.findByName(name);
        return genre.orElseGet(() -> genres.save(new Genre(name)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAuthors() {
        return authors.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getGenres() {
        return genres.findAll();
    }

    @Transactional
    @Override
    public Comment addComment(long bookID, String text) {
        Book book = books.findById(bookID).get();
        if (book != null) {
            Comment comment = new Comment(text, book);
            comments.save(comment);
            return comment;
        }
        return null;
    }

    @Transactional
    @Override
    public void changeComment(long commentID, String text) {
        comments.updateTextById(commentID, text);
    }

    @Transactional
    @Override
    public void removeComment(long commentID) {
        comments.deleteById(commentID);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getComments(long bookId) {
        return comments.findByBookId(bookId);
    }
}
