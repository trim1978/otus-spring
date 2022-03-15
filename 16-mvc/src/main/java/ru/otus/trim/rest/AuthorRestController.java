package ru.otus.trim.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.trim.model.Author;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.rest.dto.AuthorDto;
import ru.otus.trim.rest.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorRestController {

    private final AuthorRepository repository;

    public AuthorRestController(AuthorRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/rest_authors", method = RequestMethod.GET, params = {})
    public List<AuthorDto> getAllAuthors() {
        return repository.findAll().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/rest_author", method = RequestMethod.GET)
    public AuthorDto getAuthorByIdInRequest(@RequestParam("id") int id) {
        Author author = repository.findById(id).orElseThrow(NotFoundException::new);
        return AuthorDto.toDto(author);
    }

    @GetMapping("/rest_author/{id}")
    public AuthorDto getAuthorByIdInPath(@PathVariable("id") int id) {
        Author author = repository.findById(id).orElseThrow(NotFoundException::new);
        return AuthorDto.toDto(author);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Такого автора нет");
    }
}
