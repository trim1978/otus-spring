package ru.otus.trim.service;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.trim.model.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирования решения проблемы N+1")
@DataJpaTest
@Import(LibraryServiceImpl.class)
class LibraryNplusOneTest {

    @Autowired
    private LibraryServiceImpl library;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужной книге по её id")
    @Test
    void shouldFindExpectedBookById() {
        val optionalActualBook = library.getBookById(1L);
        val expectedBook = em.find(Book.class, 1L);
        assertThat(optionalActualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val comments = library.getBooks();
        assertThat(comments).isNotNull().hasSize(2)
                .allMatch(s -> !s.getTitle().equals(""))
                .allMatch(s -> s.getAuthors().size() > 0)
                .allMatch(s -> s.getGenres().size() > 0);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(3);
    }

    @DisplayName("должен загружать список всех комментариев с полной информацией о них")
    //@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCorrectCommentsListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        sessionFactory.getStatistics().clear();

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val comments = library.getComments(2);
        assertThat(comments).isNotNull().hasSize(4)
                .allMatch(s -> !s.getText().equals(""))
                .allMatch(s -> s.getBook() != null && s.getBook().getTitle() != null);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(2);
    }
}