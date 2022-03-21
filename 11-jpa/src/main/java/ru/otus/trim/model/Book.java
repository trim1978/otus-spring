package ru.otus.trim.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
@ToString
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = CascadeType.DETACH, targetEntity = Author.class, fetch = FetchType.LAZY)
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book"),
            inverseJoinColumns = @JoinColumn(name = "author"))
    //@ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Author> authors;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = CascadeType.DETACH, targetEntity = Genre.class, fetch = FetchType.LAZY)
    @JoinTable(name = "book_genres",
            joinColumns = @JoinColumn(name = "book"),
            inverseJoinColumns = @JoinColumn(name = "genre"))
    //@ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Genre> genres;

    public Book(String title, Author author, Genre genre) {
        this (0, title, List.of(author), List.of(genre));
    }

}
