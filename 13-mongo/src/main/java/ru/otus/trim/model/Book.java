package ru.otus.trim.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "books")
public class Book {
    @Transient
    public static final String SEQUENCE_NAME = "books_sequence";

    @Id // Позволяет указать какое поле является идентификатором
    private long id;
    private String title;
    @DBRef
    private Author author;
    private List<String> genres;

    public Book(String title, String author, String genres) {
        this(title,new Author (author), List.of(genres));
    }

    public Book(String title, Author author, String genres) {
        this(title,author,List.of(genres));
    }

    public Book(String title, Author author, List<String> genres) {
        this.title = title;
        this.author = author;
        this.genres = genres;
    }

    public Book(long id, String title, Author author, List<String> genres) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genres = genres;
    }

    public Book() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genres=" + genres +
                '}';
    }
}
