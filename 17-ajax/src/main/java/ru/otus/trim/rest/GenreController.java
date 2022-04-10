package ru.otus.trim.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.trim.repository.GenreRepository;
import ru.otus.trim.rest.dto.GenreDto;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class GenreController {
    private final GenreRepository genreRepository;

    @GetMapping("/api/genres")
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }
}
