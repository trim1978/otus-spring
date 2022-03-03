package ru.otus.trim.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import ru.otus.trim.domain.QuizAction;

public class QuizActionEvent extends ApplicationEvent {

    @Getter
    @Setter
    private QuizAction quizAction;

    public QuizActionEvent(Object source) {
        super(source);
    }
}
