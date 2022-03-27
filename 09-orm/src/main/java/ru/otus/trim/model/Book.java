package ru.otus.trim.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;


@Entity // Указывает, что данный класс является сущностью
@Table(name = "books") // Задает имя таблицы, на которую будет отображаться сущность@AllArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "author-genre-entity-graph",attributeNodes = {@NamedAttributeNode("author"),@NamedAttributeNode("genre")})
@ToString()
@Getter
@Setter
public class Book {
    @Id // Позволяет указать какое поле является идентификатором
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия генерации идентификаторов
    private long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    // Задает поле, по которому происходит объединение с таблицей для хранения связанной сущности
    @JoinColumn(name = "author")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Author author;

    @ManyToOne(cascade = CascadeType.ALL)
    // Задает поле, по которому происходит объединение с таблицей для хранения связанной сущности
    @JoinColumn(name = "genre")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Genre genre;
}
