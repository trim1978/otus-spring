package ru.otus.trim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.model.Genre;
import ru.otus.trim.rest.dto.AuthorDto;
import ru.otus.trim.rest.dto.BookDto;
import ru.otus.trim.rest.dto.GenreDto;
import ru.otus.trim.rest.exceptions.NotFoundException;
import ru.otus.trim.service.LibraryService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(BookController.class)
class BookControllerTest {
    private static final Author AUTHOR = new Author(1, "test");
    private static final Genre GENRE = new Genre(1, "test");
    private static final Book BOOK = new Book(1L, "test", AUTHOR, GENRE, List.of());
    private static final Comment COMMENT = new Comment("text", BOOK);


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService library;

    @Test
    void shouldReturnCorrectForList () throws Exception {
        when (library.getBooks(Mockito.any())).thenReturn(new PageImpl<>(List.of(BOOK)));
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("book_list"))
                .andExpect(model().attribute("books", List.of(BookDto.toDto(BOOK))))
        ;
    }

    @Test
    public void shouldReturnCorrectForEdit() throws Exception {
        when(library.getBookById(Mockito.anyLong())).thenReturn(BOOK);
        when(library.getAuthors()).thenReturn(List.of(AUTHOR));
        when(library.getGenres()).thenReturn(List.of(GENRE));
        this.mockMvc.perform(get("/book").param("id", "1"))
                .andExpect(view().name("book_edit"))
                .andExpect(model().attribute("book", BookDto.toDto(BOOK)))
                .andExpect(model().attribute("authors", List.of(AuthorDto.toDto(AUTHOR))))
                .andExpect(model().attribute("genres", List.of(GenreDto.toDto(GENRE))))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDoCorrectForRemove() throws Exception {
        when(library.getBookById(Mockito.anyLong())).thenReturn(BOOK);
        this.mockMvc.perform(delete("/book_remove").param("id", "1"))
                .andExpect(redirectedUrl("/books"))
                .andExpect(view().name("redirect:/books"))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    public void shouldReturnCorrectForAdd() throws Exception {
        when(library.getAuthors()).thenReturn(List.of(AUTHOR));
        when(library.getGenres()).thenReturn(List.of(GENRE));
        this.mockMvc.perform(get("/book").param("id", "0"))
                .andExpect(view().name("book_edit"))
                .andExpect(model().attribute("book", new BookDto (0, "", null, null)))
                .andExpect(model().attribute("authors", List.of(AuthorDto.toDto(AUTHOR))))
                .andExpect(model().attribute("genres", List.of(GenreDto.toDto(GENRE))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnErrorNotFound() throws Exception {
        when(library.getBookById(3L)).thenThrow(NotFoundException.class);
        this.mockMvc.perform(get("/book").param("id", "3"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Такой книги нет"));
    }

    @Test
    void shouldReturnCorrectForSave() throws Exception {
        when(library.updateBook(BOOK)).thenReturn(BOOK);
        this.mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .flashAttr("book", BookDto.toDto(BOOK)))
                .andExpect(redirectedUrl("/books"))
                .andExpect(view().name("redirect:/books"))
                .andExpect(status().is3xxRedirection());
        verify(library, times(1)).updateBook(any(Book.class));
    }

}