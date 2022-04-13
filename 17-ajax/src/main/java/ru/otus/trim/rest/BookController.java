package ru.otus.trim.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RestController
public class BookController {
    private final LibraryService library;

    @GetMapping("/api/books")
    public List<BookDto> getBooksByPage(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "step", defaultValue = "10") int pageCapacity) {
        Pageable pageable = PageRequest.of(page, pageCapacity);
        Page<Book> books = library.getBooks(pageable);
            return books.stream()
                    .map(BookDto::toDto)
                    .collect(Collectors.toList());
    }

    @GetMapping("/api/book")
    public BookDto getBook(@RequestParam(name = "id", defaultValue = "0") long id) {
        return id > 0 ? BookDto.toDto (Optional.ofNullable(library.getBookById(id)).orElseThrow(NotFoundException:: new)) : new BookDto (0, "", new AuthorDto(0, ""), new GenreDto(0, ""));
    }

    @GetMapping("/api/books_count")
    public long getBooksCount() {
        return library.getBooksCount();
    }

    @DeleteMapping("/api/book_remove")
    public boolean removeBookById(@RequestParam("id") Long id) {
        Book book = Optional.ofNullable(library.getBookById(id)).orElseThrow(NotFoundException :: new);
        library.removeBookById(id);
        return true;
    }

    @PostMapping("/api/book")
    public BookDto saveBook(@RequestBody BookDto book) {
        Book result;
        if (book.getId() == 0){
            result = library.addBook(book.getTitle(), library.getAuthor(book.getAuthor().getId()).getName(), library.getGenre(book.getGenre().getId()).getName());
        }
        else
        {
            result = library.updateBook(book.toDomainObject());
        }
        return BookDto.toDto (result);
    }

    @PatchMapping("/api/books_generate")
    public boolean generateBooks(@RequestParam("count") int count) {
        List<Genre> genres = library.getGenres();
        List<Author> authors = library.getAuthors();
        if (genres.size() == 0){
            genres.add(new Genre ("drama"));
        }
        if (authors.size() == 0){
            authors.add(new Author("Pushkin"));
        }

        for (int i=0; i<count; i++) {
            library.addBook(UUID.randomUUID().toString(), authors.get((int)(authors.size()*Math.random())).getName(), genres.get((int)(genres.size()*Math.random())).getName());
        }
        return true;
    }
}
