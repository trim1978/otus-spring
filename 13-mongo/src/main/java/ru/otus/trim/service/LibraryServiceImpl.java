package ru.otus.trim.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.trim.model.Comment;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;

import java.util.*;

@Service
public class LibraryServiceImpl implements LibraryService {
    public final BookRepository books;
    public final AuthorRepository authors;
    public final CommentRepository comments;

    public LibraryServiceImpl(BookRepository books, AuthorRepository authors, CommentRepository comments) {
        this.books = books;
        this.authors = authors;
        this.comments = comments;
    }

    @Transactional
    @Override
    public Book changeBook(String bookId, String title, String author, List<String> genres) {
        Book book = getBookById(bookId);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(getAuthor(author));
            book.setGenres(genres);
            return books.save(book);
        }
        return null;
    }

    @Transactional
    @Override
    public Book removeBookById(String bookID) {
        Book book = getBookById(bookID);
        if (book != null) {
            comments.deleteByBook(book);
            books.delete(book);
        }
        return book;
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBookById(String bookID) {
        Optional<Book> opt = books.findById(bookID);
        return opt.orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooks() {
        return books.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Author getAuthor(String name) {
        Author author = authors.findByName(name);
        if (author == null){
            author = authors.save(new Author(name));
        }
        return author;
    }

    @Transactional
    @Override
    public Book addBook(String title, String author, String genre) {
        Book book = new Book(title, getAuthor(author), genre);
        book = books.save(book);
        return book;
    }

    @Transactional
    @Override
    public Author removeAuthor(String name) {
        Author author = authors.findByName(name);
        if (author != null){
            List<Book> removed = books.deleteByAuthor(author);
            for (Book book : removed) comments.deleteByBook(book);
            authors.delete(author);
        }
        return author;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooksByAuthor(String name) {
        Author author = authors.findByName(name);
        if (author != null) {
            return books.findByAuthor(author);
        }
        return List.of();
    }

    @Transactional()
    @Override
    public Comment addComment(String bookId, String text) {
        Comment comment = new Comment(books.findById(bookId).orElseThrow(), text);
        return comments.save(comment);
    }

    @Transactional()
    @Override
    public Comment changeComment(String commentID, String text) {
        Comment comment = comments.findById(commentID).orElseThrow();
        comment.setText(text);
        return comments.save(comment);
    }

    @Transactional()
    @Override
    public void removeComment(String commentID) {
        comments.deleteById(commentID);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getComments(String bookId) {
        return comments.findByBookId(bookId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAuthors() {
        return authors.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getGenres() {
        List<Book> booksAll = books.findAll();
        HashSet<String> genres = new HashSet<>();
        for (Book book : booksAll) {
            genres.addAll(book.getGenres());
        }
        return new ArrayList<>(genres);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooksByGenre(String genre) {
        return books.findByGenres(genre);
    }
}
