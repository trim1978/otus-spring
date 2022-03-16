package ru.otus.trim.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Genre;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.GenreRepository;
import ru.otus.trim.rest.dto.AuthorDto;
import ru.otus.trim.rest.dto.BookDto;
import ru.otus.trim.rest.dto.GenreDto;
import ru.otus.trim.service.LibraryService;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class BookController {
    private final LibraryService library;

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books",library.getBooks().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList()));
        return "book_list";
    }

    @GetMapping("/books_generate")
    public String generateBooks(@RequestParam("count") int count) {
        List<Genre> genres = library.getGenres();
        List<Author> authors = library.getAuthors();
        for (int i=0; i<count; i++) {
            library.addBook(UUID.randomUUID().toString(), authors.get((int)(authors.size()*Math.random())).getName(), genres.get((int)(genres.size()*Math.random())).getName());
        }
        return "redirect:/books";
    }


    @GetMapping("/book")
    public String getBook(@RequestParam("id") long id, Model model) {
        model.addAttribute("book",id > 0 ? BookDto.toDto (library.getBookById(id)) : new BookDto (0, "", new AuthorDto (0, ""), new GenreDto (0, "")));

        model.addAttribute("genres",library.getGenres().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList()));

        model.addAttribute("authors",library.getAuthors().stream()
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
        library.updateBook(book.toDomainObject());
        return "redirect:/books";
    }

}
