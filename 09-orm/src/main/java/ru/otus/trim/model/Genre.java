package ru.otus.trim.model;

import lombok.*;

import javax.persistence.*;


@Entity // Указывает, что данный класс является сущностью
@Table(name = "genres") // Задает имя таблицы, на которую будет отображаться сущность@AllArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@Getter
@Setter
public class Genre {
    @Id // Позволяет указать какое поле является идентификатором
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия генерации идентификаторов
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
