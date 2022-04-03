package ru.otus.trim.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.otus.trim.model.CommentSequence;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Data
@AllArgsConstructor
@Service
public class SequenceService {
    private final MongoTemplate template;


    public long generateSequence(String seqName) {
        CommentSequence sequence = template.findAndModify(
                new Query(Criteria.where("_id").is(seqName)),
                new Update().inc("count", 1),
                options().returnNew(true).upsert(true),
                CommentSequence.class
                );

        return !Objects.isNull(sequence) ? sequence.getCount() : 1;
    }
}
