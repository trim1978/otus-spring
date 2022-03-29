package ru.otus.trim.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class Author {
    @Id
    private int id;
    private String name;
    @Transient
    public static final String SEQUENCE_NAME = "autors_sequence";

    public Author(String name) {
        this.name = name;
    }
}
