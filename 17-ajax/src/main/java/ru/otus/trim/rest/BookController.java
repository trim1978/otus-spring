package ru.otus.trim.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.otus.trim.model.Book;
import ru.otus.trim.rest.dto.AuthorDto;
import ru.otus.trim.rest.dto.BookDto;
import ru.otus.trim.rest.dto.GenreDto;
import ru.otus.trim.rest.exceptions.NotFoundException;
import ru.otus.trim.service.LibraryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class BookController {
    private final LibraryService library;

    @GetMapping("/api/books/")
    public List<BookDto> getBooksByPage(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "step", defaultValue = "10") int pageCapacity) {
        Pageable pageable = PageRequest.of(page, pageCapacity);
        Page<Book> books = library.getBooks(pageable);
        return books.stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable long id) {
        return BookDto.toDto(Optional.ofNullable(library.getBookById(id)).orElseThrow(NotFoundException::new));
    }

    @GetMapping("/api/books/?count")
    public long getBooksCount() {
        return library.getBooksCount();
    }

    @DeleteMapping("/api/books/{id}")
    public void removeBookById(@PathVariable Long id) {
        library.removeBookById(id);
    }

    @PostMapping("/api/books/")
    public void saveBook(@RequestBody BookDto book) {
        if (book.getId() == 0) {
            library.addBook(book.getTitle(), library.getAuthor(book.getAuthor().getId()).getName(), library.getGenre(book.getGenre().getId()).getName());
        } else {
            library.updateBook(book.toDomainObject());
        }
    }
}
