package ru.otus.trim.model;

import lombok.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
