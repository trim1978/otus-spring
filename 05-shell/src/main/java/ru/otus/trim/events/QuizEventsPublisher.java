package ru.otus.trim.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.otus.trim.domain.QuizAction;

@Service
@RequiredArgsConstructor
public class QuizEventsPublisher implements QuizActionPublisher {

    private final ApplicationEventPublisher publisher;


    @Override
    public QuizAction runQuiz() {
        QuizActionEvent event = new QuizActionEvent(this);
        publisher.publishEvent(event);
        return event.getQuizAction();
    }
}
