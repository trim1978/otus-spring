package ru.otus.trim.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "comment_sequence")
public class CommentSequence {
    @Id
    private String id;
    private Long count;
}
