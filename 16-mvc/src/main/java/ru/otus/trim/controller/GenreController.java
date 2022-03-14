package ru.otus.trim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.trim.repository.GenreRepository;
import ru.otus.trim.rest.dto.GenreDto;

import java.util.stream.Collectors;

@Controller
public class GenreController {
    private final GenreRepository repository;

    public GenreController(GenreRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/genres")
    public String getAllGenres(Model model) {
        model.addAttribute("genres",repository.findAll().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList()));
        return "genre_list";
    }
}
