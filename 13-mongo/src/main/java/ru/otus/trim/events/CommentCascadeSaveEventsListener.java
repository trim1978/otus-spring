package ru.otus.trim.events;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.trim.model.Comment;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.service.SequenceGeneratorService;

import java.util.Date;

@Component
public class CommentCascadeSaveEventsListener extends AbstractMongoEventListener<Comment> {
    private final BookRepository bookRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public CommentCascadeSaveEventsListener(BookRepository bookRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.bookRepository = bookRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Comment> event) {
        //System.out.println(" BEFORE " + event);
        Comment comment = event.getSource();
        if (comment.getId() < 1) {
            comment.setDatetime(new Date());
            comment.setId(sequenceGeneratorService.generateLongSequence(Comment.SEQUENCE_NAME));
        }
        super.onBeforeConvert(event);
        if (comment.getBook() != null && comment.getBook().getId() <= 0) {
            bookRepository.save(comment.getBook());//.stream().filter(e -> Objects.isNull(e.getId())).forEach(knowledgeRepository::save);
        }
    }
}