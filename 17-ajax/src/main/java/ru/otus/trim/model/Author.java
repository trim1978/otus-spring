package ru.otus.trim.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
@ToString
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

//    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "authors")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private List<Book> books;
//
//    public Author(String name) {
//        this( 0, name, List.of());
//    }

    public Author(String name) {
        this.name = name;
    }
}
