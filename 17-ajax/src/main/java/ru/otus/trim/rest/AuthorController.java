package ru.otus.trim.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.trim.model.Author;
import ru.otus.trim.rest.dto.AuthorDto;
import ru.otus.trim.rest.exceptions.NotFoundException;
import ru.otus.trim.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class AuthorController {

    private final LibraryService library;

    @GetMapping(value = "/api/authors/")
    public List<AuthorDto> getAllAuthors() {
        return library.getAuthors().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/api/authors/{id}")
    public AuthorDto getAuthorByIdInRequest(@RequestParam("id") int id) {
        Author author = library.getAuthor(id);
        if (author == null) throw new NotFoundException();
        return AuthorDto.toDto(author);
    }

    @PostMapping("/api/authors/")
    public void saveAuthor(@ModelAttribute("author") AuthorDto author) {
        library.setAuthor(author.toDomainObject());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Такого автора нет");
    }
}
