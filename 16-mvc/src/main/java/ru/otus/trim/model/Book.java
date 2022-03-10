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

    @ManyToOne()
    // Задает поле, по которому происходит объединение с таблицей для хранения связанной сущности
    @JoinColumn(name = "author", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Author author;

    @ManyToOne()
    // Задает поле, по которому происходит объединение с таблицей для хранения связанной сущности
    @JoinColumn(name = "genre", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Genre genre;

    @OneToMany(targetEntity = Comment.class, mappedBy = "book", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Comment> comments;

    public Book(String title, Author author, Genre genre) {
        this (0, title, author, genre, List.of());
    }

    // для совместимости теста
    public List<Genre> getGenres (){
        return  List.of(genre);
    }
    public List<Author> getAuthors (){
        return  List.of(author);
    }
}
