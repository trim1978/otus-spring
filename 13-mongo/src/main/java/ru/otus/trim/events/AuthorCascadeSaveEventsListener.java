package ru.otus.trim.events;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.trim.model.Author;
import ru.otus.trim.service.SequenceGeneratorService;

@Component
public class AuthorCascadeSaveEventsListener extends AbstractMongoEventListener<Author> {
    private final SequenceGeneratorService sequenceGeneratorService;

    public AuthorCascadeSaveEventsListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Author> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateIntSequence(Author.SEQUENCE_NAME));
        }
        super.onBeforeConvert(event);
    }
}
