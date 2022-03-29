package ru.otus.trim.events;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.trim.model.Book;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.service.SequenceGeneratorService;

@Component
public class BookCascadeSaveEventsListener extends AbstractMongoEventListener<Book> {
    private final SequenceGeneratorService sequenceGeneratorService;
    private final AuthorRepository authorRepository;

    public BookCascadeSaveEventsListener(SequenceGeneratorService sequenceGeneratorService, AuthorRepository authorRepository) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.authorRepository = authorRepository;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        //System.out.println(" BEFORE " + event);
        if (event.getSource().getAuthor().getId() < 1) {
            authorRepository.save(event.getSource().getAuthor());
        }
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateLongSequence(Book.SEQUENCE_NAME));
        }
        super.onBeforeConvert(event);
    }
}
