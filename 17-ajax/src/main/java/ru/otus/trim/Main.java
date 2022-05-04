package ru.otus.trim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Genre;
import ru.otus.trim.service.LibraryService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Autowired
    private LibraryService library;

    @PostConstruct
    public void generate ()
    {
        int count = 10;
        List<Genre> genres = library.getGenres();
        List<Author> authors = library.getAuthors();
        if (genres.size() == 0){
            genres.add(new Genre ("drama"));
        }
        if (authors.size() == 0){
            authors.add(new Author("Pushkin"));
        }

        for (int i=0; i<count; i++) {
            library.addBook("Book " + (i+1), authors.get((int)(authors.size()*Math.random())).getName(), genres.get((int)(genres.size()*Math.random())).getName());
        }

    }

}
