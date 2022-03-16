package ru.otus.trim.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.trim.repository.GenreRepository;
import ru.otus.trim.rest.dto.GenreDto;

import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class GenreController {
    private final GenreRepository genreRepository;

    @GetMapping("/genres")
    public String getAllGenres(Model model) {
        model.addAttribute("genres",genreRepository.findAll().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList()));
        return "genre_list";
    }
}
