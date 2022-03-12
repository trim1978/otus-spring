package ru.otus.trim.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.trim.model.Comment;
import ru.otus.trim.service.LibraryServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Репозиторий CRUD для коментариев")
@DataJpaTest
@Import(LibraryServiceImpl.class)
@ExtendWith(MockitoExtension.class)
class CommentRepositoryTest {

    @Autowired
    private LibraryServiceImpl library;
    @MockBean
    private CommentRepository comments;

    @DisplayName("select")
    @Test
    void shouldFindAllComments() {
        library.getComments(1L);
        verify(comments, times(1)).findByBookId(1L);
    }

    @DisplayName("insert")
    @Test
    void shouldAddComment() {
        library.addComment(1, "comment");
        verify(comments, times(1)).saveAndFlush(any());
    }

    @DisplayName("update")
    @Test
    void shouldSetComment() {
        library.addComment(1, "comment");
        verify(comments, times(1)).saveAndFlush(any());
    }

    @DisplayName("delete")
    @Test
    void shouldDelComment() {
        library.removeComment(1L);
        verify(comments, times(1)).deleteById(1L);
    }
}