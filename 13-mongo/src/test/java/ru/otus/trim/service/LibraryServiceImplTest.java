package ru.otus.trim.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.trim.model.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий коментариев должен выполнять")
@ComponentScan("ru.otus.trim")
@SpringBootTest
@Import(LibraryServiceImpl.class)
class LibraryServiceImplTest {

    @Autowired
    private LibraryServiceImpl library;

    @DisplayName("select")
    @Test
    void shouldFindAllComments() {
        assertThat(library.getComments("1")).hasSize(2);
        assertThat(library.getComments("2")).hasSize(4);
    }

    @DisplayName("insert")
    @Test
    void shouldAddComment() {
        Comment comment = library.addComment("1", "comment");
        assertThat(comment).matches(t -> t.getText().equalsIgnoreCase("comment"))
                .matches(t -> t.getDatetime().getTime() > 0L)
                .matches(t -> t.getId() != null);
        assertThat(comment).isIn(library.getComments("1"));
    }

    @DisplayName("update")
    @Test
    void shouldSetComment() {
        Comment comment = library.addComment("1", "comment");
        library.changeComment(comment.getId(),"changed");
        assertThat(comment).matches(t -> t.getText().equalsIgnoreCase("changed"))
                .matches(t -> t.getDatetime().getTime() > 0L)
                .matches(t -> t.getId() != null);
        assertThat(comment).isIn(library.getComments("1"));
        assertThat(library.changeComment("100","changed")).isNull();
    }

    @DisplayName("delete")
    @Test
    void shouldDelComment() {
        Comment comment = library.addComment("1", "comment");
        assertThat(comment).isIn(library.getComments("1"));
        library.removeComment(comment.getId());
        assertThat(comment).isNotIn(library.getComments("1"));
    }
}