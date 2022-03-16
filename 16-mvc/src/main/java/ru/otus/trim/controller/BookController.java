package ru.otus.trim.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.GenreRepository;
import ru.otus.trim.rest.dto.AuthorDto;
import ru.otus.trim.rest.dto.BookDto;
import ru.otus.trim.rest.dto.GenreDto;

import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books",bookRepository.findAll().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList()));
        return "book_list";
    }

    @GetMapping("/book")
    public String getBook(@RequestParam("id") long id, Model model) {
        model.addAttribute("book",id > 0 ? BookDto.toDto (bookRepository.findById(id).orElseThrow()) : new BookDto (0, "", new AuthorDto (0, ""), new GenreDto (0, "")));

        model.addAttribute("genres",genreRepository.findAll().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList()));

        model.addAttribute("authors",authorRepository.findAll().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList()));

        return "book_edit";
    }

    @PostMapping("/book")
    public String saveBook(@ModelAttribute("book") BookDto book,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "book_edit";
        }
        bookRepository.save(book.toDomainObject());
        return "redirect:/books";
    }

}