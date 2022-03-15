package ru.otus.trim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/author")
    public String getBook(@RequestParam("id") int id, Model model) {
        model.addAttribute("author",AuthorDto.toDto (repository.findById(id).orElseThrow()));
        return "author_edit";
    }

    @PostMapping("/author")
    public String savePerson(@ModelAttribute("author") AuthorDto author,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "author_edit";
        }
        repository.save(author.toDomainObject());
        return "redirect:/authors";
    }

}
