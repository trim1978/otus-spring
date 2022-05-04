package ru.otus.trim.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Comment> getBookCommentById(String bookId) {
        val aggregation = newAggregation(
                match(Criteria.where("id").is(bookId))
                , unwind("comment")
                , project().andExclude("_id").and("comment.id").as("_id").and("comment.text").as("text")
        );
        return mongoTemplate.aggregate(aggregation, Book.class, Comment.class).getMappedResults();
    }
}
