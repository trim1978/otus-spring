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
    public void setBook(Book book) {
        /*if (book.getId() > 0) {
            Book book1 = getBookById(book.getId());
            book1.setGenres(book.getGenres());
            //authors.save(book.getAuthor());
            book1.setAuthor(book.getAuthor());
            books.save(book1);
        }
        else */books.save(book);
    }

    @Transactional
    @Override
    public Book removeBookById(long bookID) {
        Book book = getBookById(bookID);
        if (book != null) {
            books.delete(book);
            for (Comment c : comments.findByBook(book)) comments.delete(c);
        }
        return book;
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBookById(long bookID) {
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
            for (Book book : books.findByAuthor (author)) removeBookById(book.getId ());
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
    public Comment addComment(long bookId, String text) {
        Comment comment = new Comment(books.findById(bookId).orElseThrow(), text);
        return comments.save(comment);
    }

    @Transactional()
    @Override
    public Comment changeComment(long commentID, String text) {
        Comment comment = comments.findById(commentID).orElseThrow();
        comment.setText(text);
        return comments.save(comment);
    }

    @Transactional()
    @Override
    public void removeComment(long commentID) {
        comments.deleteById(commentID);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getComments(long bookId) {
        return comments.findByBook(books.findById(bookId).orElseThrow());
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
    public List<Comment> getCommentsByBookId(long bookID) {
        Book book = getBookById(bookID);
        if (book != null) {
            return comments.findByBook(book);
        }
        return List.of();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooksByGenre(String genre) {
        return books.findByGenres(genre);
    }
}
