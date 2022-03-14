package ru.otus.trim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.rest.dto.AuthorDto;

import java.util.stream.Collectors;

@Controller
public class AuthorController {
    private final AuthorRepository repository;

    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/authors")
    public String getAllAuthors(Model model) {
        model.addAttribute("authors",repository.findAll().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList()));
        return "author_list";
    }

    @GetMapping("/author/{id}")
    public String getBook(Model model, @PathVariable int id) {
        model.addAttribute("author",AuthorDto.toDto (repository.findById(id).orElseThrow()));
        return "author_edit";
    }

    @GetMapping("/r")
    public String getAllAuthors() {
        return "redirect:all ";
    }
}
