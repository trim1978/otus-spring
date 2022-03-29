package ru.otus.trim.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public Book(String title, Author author, List<String> genres) {
        this.title = title;
        this.author = author;
        this.genres = genres;
    }

    public Book(String title, Author author, String genre) {
        this.title = title;
        this.author = author;
        this.genres = List.of(genre);
    }
}