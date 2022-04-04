package ru.otus.trim.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/api/book")
    public BookDto getBook(@RequestParam(name = "id", defaultValue = "0") long id, Model model) {
        return id > 0 ? BookDto.toDto (Optional.ofNullable(library.getBookById(id)).orElseThrow(NotFoundException:: new)) : new BookDto (0, "", new AuthorDto(0, ""), new GenreDto(0, ""));
    }

    @DeleteMapping("/api/book_remove")
    public boolean removeBookById(@RequestParam("id") Long id) {
        Book book = Optional.ofNullable(library.getBookById(id)).orElseThrow(NotFoundException :: new);
        library.removeBookById(id);
        return true;
    }

    @GetMapping("/api/books_generate")
    public boolean generateBooks(@RequestParam("count") int count) {
        List<Genre> genres = library.getGenres();
        List<Author> authors = library.getAuthors();
        for (int i=0; i<count; i++) {
            library.addBook(UUID.randomUUID().toString(), authors.get((int)(authors.size()*Math.random())).getName(), genres.get((int)(genres.size()*Math.random())).getName());
        }
        return true;
    }
}
