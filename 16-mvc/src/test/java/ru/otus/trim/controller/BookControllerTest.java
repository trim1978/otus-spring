package ru.otus.trim.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.trim.service.LibraryService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService library;
    //@Autowired
    //private WebApplicationContext wac;
    //@Before
    //public void setup() {
        //mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    //}
    @Test
    void should () throws Exception {
        when (library.getBooks(Mockito.any())).thenReturn(Page.empty());
        mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk());//.andExpect(view().name("books"));
    }

//    @Test
//    void shouldReturnCorrectPersonsList() throws Exception {
//        List<Author> persons = List.of(new Author(1, "Person1"), new Author(2, "Person2"));
//        given(repository.findAll()).willReturn(persons);
//
//        List<AuthorDto> expectedResult = persons.stream()
//                .map(AuthorDto::toDto).collect(Collectors.toList());
//
//        mvc.perform(get("/authors/all"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
//    }
//
//    @Test
//    void shouldReturnCorrectPersonByIdInRequest() throws Exception {
//        Author person = new Author(1, "Person1");
//        given(repository.findById(1)).willReturn(Optional.of(person));
//        AuthorDto expectedResult = AuthorDto.toDto(person);
//
//        mvc.perform(get("/authors").param("id", "1"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
//    }
//
//    @Test
//    void shouldReturnCorrectPersonByIdInPath() throws Exception {
//        Author person = new Author(1, "Person1");
//        given(repository.findById(1)).willReturn(Optional.of(person));
//        AuthorDto expectedResult = AuthorDto.toDto(person);
//
//        mvc.perform(get("/authors/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
//    }
//
//    @Test
//    void shouldReturnExpectedErrorWhenPersonNotFound() throws Exception {
//        given(repository.findById(1)).willReturn(Optional.empty());
//
//        mvc.perform(get("/authors").param("id", "1"))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Такого автора нет"));
//
//        mvc.perform(get("/authors/1"))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Такого автора нет"));
//    }
//
//
}