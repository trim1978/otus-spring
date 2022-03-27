package ru.otus.trim.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.trim.model.Book;

import javax.persistence.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<Book> findById (long bookID) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("author-genre-entity-graph");
        Map<String, Object> properties = Map.of ("javax.persistence.fetchgraph", entityGraph);
        Book book = entityManager.find(Book.class, bookID, properties);
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("author-genre-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public void deleteById(long bookID) {
        Query query = entityManager.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", bookID);
        query.executeUpdate();
    }
}
