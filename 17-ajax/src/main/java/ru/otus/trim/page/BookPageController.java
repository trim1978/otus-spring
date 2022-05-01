package ru.otus.trim.page;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.trim.rest.BookController;
import ru.otus.trim.rest.dto.AuthorDto;
import ru.otus.trim.rest.dto.BookDto;
import ru.otus.trim.rest.dto.GenreDto;
import ru.otus.trim.rest.exceptions.NotFoundException;

@AllArgsConstructor
@Controller
public class BookPageController {
    private final BookController books;

    @GetMapping("/books/")
    public String getBooksByPage(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "step", defaultValue = "10") int pageCapacity, Model model) {
        model.addAttribute("step", pageCapacity);
        model.addAttribute("page", page);
        model.addAttribute("total", books.getBooksCount());
        return "book_list";
    }

    @GetMapping("/books/{id}")
    public String getBook(@PathVariable long id, Model model) {
        model.addAttribute("book", id > 0 ? books.getBook(id) : new BookDto(0, "", new AuthorDto(0, ""), new GenreDto(0, "")));
        return "book_edit";
    }

    @PostMapping("/books/")
    public String saveBook(@ModelAttribute("book") BookDto book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book_edit";
        }
        books.saveBook(book);
        return "redirect:/books/";
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
