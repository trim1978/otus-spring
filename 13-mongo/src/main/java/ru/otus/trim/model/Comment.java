package ru.otus.trim.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id // Позволяет указать какое поле является идентификатором
    private String id;
    private Date datetime;
    @DBRef
    private Book book;
    private String text;

    public Comment(Book book, String text) {
        this.datetime = new Date ();
        this.book = book;
        this.text = text;
    }
}
