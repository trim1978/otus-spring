package ru.otus.trim.service;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.BookBrief;
import ru.otus.trim.model.Comment;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;

import java.util.*;

@AllArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
    public final BookRepository books;
    public final AuthorRepository authors;
    public final CommentRepository comments;

    @Transactional
    @Override
    public void changeBook(String bookId, String title, String author, List<String> genres) {
        books.updateBook(bookId, title, getAuthor(author), genres);
    }

    @Transactional
    @Override
    public void removeBookById(String bookId) {
           books.deleteById(bookId);
           comments.deleteByBook(new BookBrief(bookId));
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBookById(String bookId) {
        Optional<Book> opt = books.findById(bookId);
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
        Book book = books.findById(bookId).orElseThrow();
        Comment comment = new Comment(new ObjectId().toString(), new Date (), new BookBrief(bookId), text);
        return comments.save(comment);
    }

    @Transactional()
    @Override
    public void changeComment(String commentId, String text) {
        comments.updateComment(commentId, text);
    }

    @Transactional()
    @Override
    public void removeComment(String commentId) {
        comments.deleteById(commentId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getComments(String bookId) {
        return comments.findByBook(new BookBrief(bookId));
    }

    @Override
    public Comment getComment(String commentId) {
        return comments.findById(commentId).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAuthors() {
        return authors.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Set<String> getGenres() {
        return books.findAllGenres();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooksByGenre(String genre) {
        return books.findByGenres(genre);
    }
}
