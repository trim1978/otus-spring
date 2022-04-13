package ru.otus.trim.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.model.Genre;
import ru.otus.trim.rest.dto.BookDto;
import ru.otus.trim.rest.exceptions.NotFoundException;
import ru.otus.trim.service.LibraryService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        mockMvc.perform(get("/api/books"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(library, times(1)).getBooks (any());
    }

    @Test
    public void shouldReturnCorrectForEdit() throws Exception {
        when(library.getBookById(Mockito.anyLong())).thenReturn(BOOK);
        when(library.getAuthors()).thenReturn(List.of(AUTHOR));
        when(library.getGenres()).thenReturn(List.of(GENRE));
        this.mockMvc.perform(get("/api/book").param("id", "1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(library, times(1)).getBookById(anyLong());
    }

    @Test
    public void shouldDoCorrectForRemove() throws Exception {
        when (library.getBookById(1)).thenReturn(BOOK);
        this.mockMvc.perform(delete("/api/book_remove").param("id", "1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(library, times(1)).removeBookById(anyLong());
    }

    @Test
    public void shouldReturnCorrectForAdd() throws Exception {
        when (library.getBookById(0)).thenReturn(new Book());
        this.mockMvc.perform(get("/api/book").param("id", "0"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(library, times(0)).getBookById(anyLong ());
    }

    @Test
    void shouldReturnErrorNotFound() {
        when(library.getBookById(3L)).thenReturn(null);//Throw(NotFoundException.class);
        assertThatThrownBy(() -> this.mockMvc.perform(get("/api/book").param("id", "3")))
                .hasCause(new NotFoundException());
        verify(library, times(1)).getBookById(anyLong ());
    }

    @Test
    void shouldReturnCorrectForSave() throws Exception {
        when(library.updateBook(any())).thenReturn(BOOK);
        this.mockMvc.perform(post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper ().writeValueAsString(BookDto.toDto(BOOK))))
                .andExpect(status().isOk()
        );
        verify(library, times(1)).updateBook(any(Book.class));
    }
}