package ru.otus.trim.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.trim.model.Comment;
import ru.otus.trim.service.LibraryServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий коментариев")
@DataJpaTest
@RunWith(SpringRunner.class)
@Import(LibraryServiceImpl.class)
class LibraryCommentsTest {

    @Autowired
    private LibraryServiceImpl library;

    @DisplayName("select")
    @Test
    void shouldFindAllComments() {
        assertThat(library.getComments(1)).hasSize(2);
        assertThat(library.getComments(2)).hasSize(4);
    }

    @DisplayName("insert")
    @Test
    void shouldAddComment() {
        Comment comment = library.addComment(1, "comment");
        assertThat(comment).matches(t -> t.getText().equalsIgnoreCase("comment"))
                .matches(t -> t.getDatetime().getTime() > 0L)
                .matches(t -> t.getId() > 0);
        assertThat(comment).isIn(library.getComments(1));
    }

    @DisplayName("update")
    @Test
    void shouldSetComment() {
        Comment comment = library.addComment(1, "comment");
        comment = library.changeComment(comment.getId(),"changed");
        assertThat(comment).matches(t -> t.getText().equalsIgnoreCase("changed"))
                .matches(t -> t.getDatetime().getTime() > 0L)
                .matches(t -> t.getId() > 0);
        assertThat(comment).isIn(library.getComments(1));
        //assertThat(library.changeComment(100,"changed")).isNull();
    }

    @DisplayName("delete")
    @Test
    void shouldDelComment() {
        Comment comment = library.addComment(1, "comment");
        assertThat(comment).isIn(library.getComments(1));
        library.removeComment(comment.getId());
        assertThat(comment).isNotIn(library.getComments(1));
    }
}