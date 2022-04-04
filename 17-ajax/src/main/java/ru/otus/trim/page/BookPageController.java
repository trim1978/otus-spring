package ru.otus.trim.page;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Genre;
import ru.otus.trim.rest.dto.AuthorDto;
import ru.otus.trim.rest.dto.BookDto;
import ru.otus.trim.rest.dto.GenreDto;
import ru.otus.trim.rest.exceptions.NotFoundException;
import ru.otus.trim.service.LibraryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class BookPageController {
    private final LibraryService library;

    @GetMapping("/books")
    public String getBooksByPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        model.addAttribute("page", page);
        return "book_list";
    }

    @GetMapping("/book")
    public String getBook(@RequestParam(name = "id", defaultValue = "0") long id, Model model) {
        model.addAttribute("book",id > 0 ? BookDto.toDto (Optional.ofNullable(library.getBookById(id)).orElseThrow(NotFoundException :: new)) : new BookDto (0, "", new AuthorDto (0, ""), new GenreDto (0, "")));

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
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book_edit";
        }
        if (book.getId() == 0){
            library.addBook(book.getTitle(), library.getAuthor(book.getAuthor().getId()).getName(), library.getGenre(book.getGenre().getId()).getName());
        }
        else {
            library.updateBook(book.toDomainObject());
        }
        return "redirect:/books";
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Такой книги нет");
    }

    @DeleteMapping("/book_remove")
    public String removeBookById(@RequestParam("id") Long id) {
        Book book = Optional.ofNullable(library.getBookById(id)).orElseThrow(NotFoundException :: new);
        library.removeBookById(id);
        return "redirect:/books";
    }

    ///// сервисные

    @GetMapping("/")
    public String main() {
        return "redirect:/books";
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
}
