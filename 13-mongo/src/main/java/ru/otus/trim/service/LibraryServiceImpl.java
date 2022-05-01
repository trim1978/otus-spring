package ru.otus.trim.service;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.trim.model.Comment;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;

import java.util.*;

@AllArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
    public final BookRepository books;
    public final AuthorRepository authors;

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
    public void removeBookById(String bookId) {
           books.deleteById(bookId);
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
        Comment comment = new Comment(new ObjectId().toString(), new Date (), book, text);
        List<Comment> comments = book.getComment();
        if (comments == null){
            book.setComment(List.of(comment));
        }
        else {
            comments.add (comment);
        }
        books.save(book);
        return comment;
    }

    @Transactional()
    @Override
    public Comment changeComment(String bookId, String commentId, String text) {
        Book book = books.findById(bookId).orElseThrow();
        List<Comment> comments = book.getComment();
        ListIterator<Comment> it = comments.listIterator();
        for (Comment comment : comments){
            if (comment.getId().equalsIgnoreCase(commentId)){
                comment.setText(text);
                books.save(book);
                comment.setBook(book);
                return comment;
            }
        }
        return null;
    }

    @Transactional()
    @Override
    public void removeComment(String bookId, String commentId) {
        Book book = books.findById(bookId).orElseThrow();
        List<Comment> comments = book.getComment();
        ListIterator<Comment> it = comments.listIterator();
        while (it.hasNext()){
            if (it.next().getId().equalsIgnoreCase(commentId)){
                it.remove();
                books.save(book);
                break;
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getComments(String bookId) {
        Book book = books.findById(bookId).orElseThrow();
        List<Comment> comments = book.getComment();
        if (comments == null){
            return List.of();
        }
        else {
            for (Comment comment : comments){
                comment.setBook(book);
            }
            return comments;
        }
        //return books.getBookCommentById(bookId);
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
