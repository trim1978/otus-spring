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
public class GenreController {

    private final GenreRepository repository;

    public GenreController(GenreRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/genres/all", method = RequestMethod.GET, params = {})
    public List<GenreDto> getAllPersons() {
        return repository.findAll().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/genres", method = RequestMethod.GET)
    public GenreDto getPersonByIdInRequest(@RequestParam("id") int id) {
        Genre person = repository.findById(id).orElseThrow(NotFoundException::new);
        return GenreDto.toDto(person);
    }

    @GetMapping("/genres/{id}")
    public GenreDto getPersonByIdInPath(@PathVariable("id") int id) {
        Genre person = repository.findById(id).orElseThrow(NotFoundException::new);
        return GenreDto.toDto(person);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Такого жанра нет!");
    }
}
