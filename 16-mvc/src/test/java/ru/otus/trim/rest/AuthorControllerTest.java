package ru.otus.trim.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.trim.model.Author;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.rest.dto.AuthorDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorRepository repository;

    @Test
    void shouldReturnCorrectPersonsList() throws Exception {
        List<Author> persons = List.of(new Author(1, "Person1"), new Author(2, "Person2"));
        given(repository.findAll()).willReturn(persons);

        List<AuthorDto> expectedResult = persons.stream()
                .map(AuthorDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/authors/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldReturnCorrectPersonByIdInRequest() throws Exception {
        Author person = new Author(1, "Person1");
        given(repository.findById(1)).willReturn(Optional.of(person));
        AuthorDto expectedResult = AuthorDto.toDto(person);

        mvc.perform(get("/authors").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldReturnCorrectPersonByIdInPath() throws Exception {
        Author person = new Author(1, "Person1");
        given(repository.findById(1)).willReturn(Optional.of(person));
        AuthorDto expectedResult = AuthorDto.toDto(person);

        mvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldReturnExpectedErrorWhenPersonNotFound() throws Exception {
        given(repository.findById(1)).willReturn(Optional.empty());

        mvc.perform(get("/authors").param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Такого автора нет"));

        mvc.perform(get("/authors/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Такого автора нет"));
    }


}