package ru.otus.trim.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.trim.model.Genre;
import ru.otus.trim.repository.GenreRepository;
import ru.otus.trim.rest.dto.GenreDto;
import ru.otus.trim.rest.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GenreRestController {

    private final GenreRepository repository;

    public GenreRestController(GenreRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/rest_genres", method = RequestMethod.GET, params = {})
    public List<GenreDto> getAllGenres() {
        return repository.findAll().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/rest_genre", method = RequestMethod.GET)
    public GenreDto getGenreByIdInRequest(@RequestParam("id") int id) {
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        return GenreDto.toDto(genre);
    }

    @GetMapping("/rest_genre/{id}")
    public GenreDto getGenreByIdInPath(@PathVariable("id") int id) {
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        return GenreDto.toDto(genre);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Такого жанра нет!");
    }
}
