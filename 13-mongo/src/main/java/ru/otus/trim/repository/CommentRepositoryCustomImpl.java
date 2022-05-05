package ru.otus.trim.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.trim.model.BookBrief;
import ru.otus.trim.model.Comment;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    @Override
    public void updateComment(String commentId, String text) {
        Query query = new Query(new Criteria("id").is(commentId));
        Update update = new Update();
        update.set("text", text);
        mongoTemplate.updateFirst(query, update, Comment.class);
    }
}
