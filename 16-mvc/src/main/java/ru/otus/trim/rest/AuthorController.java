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
public class AuthorController {

    private final AuthorRepository repository;

    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/authors/all", method = RequestMethod.GET, params = {})
    public List<AuthorDto> getAllPersons() {
        return repository.findAll().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public AuthorDto getPersonByIdInRequest(@RequestParam("id") int id) {
        Author person = repository.findById(id).orElseThrow(NotFoundException::new);
        return AuthorDto.toDto(person);
    }

    @GetMapping("/authors/{id}")
    public AuthorDto getPersonByIdInPath(@PathVariable("id") int id) {
        Author person = repository.findById(id).orElseThrow(NotFoundException::new);
        return AuthorDto.toDto(person);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Такого автора нет");
    }
}
