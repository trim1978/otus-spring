package ru.otus.trim.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;


@Entity // Указывает, что данный класс является сущностью
@Table(name = "comments") // Задает имя таблицы, на которую будет отображаться сущность@AllArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Getter
@Setter
public class Comment {
    @Id // Позволяет указать какое поле является идентификатором
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия генерации идентификаторов
    private long id;

    @Column(name = "text", nullable = false)
    private String text;

    // При загрузке комментариев не так важно получить данные о самой книге, поэтому подойдет и такой способ
    @Fetch(FetchMode.SELECT)
    // Указывает на связь между таблицами "один к одному"
    @OneToOne(cascade = CascadeType.ALL, targetEntity = Book.class, orphanRemoval = true, fetch = FetchType.LAZY)
    // Задает поле, по которому происходит объединение с таблицей для хранения связанной сущности
    @JoinColumn(name = "book")
    //@JoinTable(name = "books",joinColumns = @JoinColumn(name = "student_id"),inverseJoinColumns = @JoinColumn(name = "course_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Book book;

    @Column(name = "datetime", nullable = false)
    private Date datetime;

    public Comment(String text, Book book) {
        this.datetime = new Date();
        this.text = text;
        this.book = book;
    }
}
