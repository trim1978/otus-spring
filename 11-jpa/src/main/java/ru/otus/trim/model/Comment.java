package ru.otus.trim.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;

    @OneToOne(orphanRemoval = true)
    // Задает поле, по которому происходит объединение с таблицей для хранения связанной сущности
    @JoinColumn(name = "book")
    private Book book;

    private Date datetime;

    public Comment(String text, Book book) {
        this.datetime = new Date();
        this.text = text;
        this.book = book;
    }
}
