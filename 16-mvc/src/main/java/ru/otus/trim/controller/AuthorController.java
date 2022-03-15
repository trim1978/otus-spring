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
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    @GetMapping("/authors")
    public String getAllAuthors(Model model) {
        model.addAttribute("authors",authorRepository.findAll().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList()));
        return "author_list";
    }

    @GetMapping("/author")
    public String getAuthor(@RequestParam("id") int id, Model model) {
        model.addAttribute("author",id > 0 ? AuthorDto.toDto (authorRepository.findById(id).orElseThrow()): new AuthorDto (0, ""));
        return "author_edit";
    }

    @PostMapping("/author")
    public String saveAuthor(@ModelAttribute("author") AuthorDto author,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "author_edit";
        }
        authorRepository.save(author.toDomainObject());
        return "redirect:/authors";
    }

}
