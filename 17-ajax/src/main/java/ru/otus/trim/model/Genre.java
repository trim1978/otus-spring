package ru.otus.trim.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
@ToString
@Getter
@Setter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

//    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "genres")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private List<Book> books;
//
//    public Genre(String name) {
//        this( 0, name, List.of());
//    }

    public Genre(String name) {
        this.name = name;
    }
}
