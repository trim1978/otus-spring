package ru.otus.trim.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.trim.model.Book;
import ru.otus.trim.rest.dto.BookDto;
import ru.otus.trim.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class BookController {
    private final LibraryService library;

    @GetMapping("/api/books")
    public List<BookDto> getBooksByPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {{
        Pageable pageable = PageRequest.of(page, 10);
        Page<Book> books = library.getBooks(pageable);
        model.addAttribute("page", page);
        model.addAttribute("total", books.getTotalPages());
            return books.stream()
                    .map(BookDto::toDto)
                    .collect(Collectors.toList());
        }
    }
}
