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
    private final EntityManager em;

    @Override
    public Optional<Book> findById (long bookID) {
        EntityGraph<?> entityGraph = em.getEntityGraph("author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.id=:id", Book.class);
        query.setParameter("id", bookID);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return Optional.ofNullable(query.getResultList().size() == 1 ? query.getSingleResult() : null);
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteById(long bookID) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", bookID);
        query.executeUpdate();
    }
}
