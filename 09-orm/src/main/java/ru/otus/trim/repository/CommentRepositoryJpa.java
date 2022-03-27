package ru.otus.trim.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.trim.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository{
    @PersistenceContext
    private final EntityManager em;

    @Override
    public void add(Comment comment){
        if (comment.getId() <= 0) {
            comment.setDatetime(new Date());
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }
    @Override
    public void deleteById(long commentID){
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", commentID);
        query.executeUpdate();
    }

    @Override
    public void deleteByBookId(long bookID) {
        Query query = em.createQuery("delete from Comment c where c.book.id = :id");
        query.setParameter("id", bookID);
        query.executeUpdate();
    }

    @Override
    public Comment update(long commentID, String text) {
        Comment comment = em.find (Comment.class, commentID);
        if (comment != null){
            comment.setText(text);
            em.merge(comment);
        }
        return comment;
    }

    @Override
    public List<Comment> getAllComments(long bookId){
        TypedQuery<Comment> query = em.createQuery("select c from Comment c join fetch c.book where c.book.id = :id", Comment.class);
        query.setParameter("id", bookId);
        return query.getResultList();
    }
}
