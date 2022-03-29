package ru.otus.trim.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class Comment {

    @Transient
    public static final String SEQUENCE_NAME = "comment_sequence";


    @Id // Позволяет указать какое поле является идентификатором
    private long id;
    private long time;
    private String text;
    @DBRef
    //@OneToMany(fetch = FetchType.LAZY)
    private Book book;

    public Comment(Book book, String text) {
        this.text = text;
        this.book = book;
    }

    public Comment() {
    }

    public Comment(long time, String text) {
        this.time = time;
        this.text = text;
    }

    public Comment(long id, long time, String text) {
        this.id = id;
        this.time = time;
        this.text = text;
    }

    public Comment(long id, long time, String text, Book book) {
        this.id = id;
        this.time = time;
        this.text = text;
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", time=" + time +
                ", text='" + text + '\'' +
                ", book=" + book +
                '}';
    }
}
