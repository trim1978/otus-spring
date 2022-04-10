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
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.rest.BookController;
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
    private final BookController books;

    @GetMapping("/books")
    public String getBooksByPage(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "step", defaultValue = "10") int pageCapacity, Model model) {
        model.addAttribute("step", pageCapacity);
        model.addAttribute("page", page);
        model.addAttribute("total", books.getBooksCount());
        return "book_list";
    }

    @GetMapping("/book")
    public String getBook(@RequestParam(name = "id", defaultValue = "0") long id, Model model) {
        model.addAttribute("book", books.getBook(id));
        return "book_edit";
    }

    @PostMapping("/book")
    public String saveBook(@ModelAttribute("book") BookDto book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book_edit";
        }
        books.saveBook(book);
        return "redirect:/books";
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Такой книги нет");
    }


    ///// сервисные

    @GetMapping("/")
    public String main() {
        return "redirect:/books";
    }
}
