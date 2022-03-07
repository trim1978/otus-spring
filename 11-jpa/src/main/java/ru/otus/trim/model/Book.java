package ru.otus.trim.model;

import lombok.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    // Указывает на связь между таблицами "один к одному"
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "author")
    private Author author;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "genre")
    private Genre genre;
}
