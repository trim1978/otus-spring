package ru.otus.trim.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.otus.trim.domain.QuizAction;
import ru.otus.trim.domain.Student;
import ru.otus.trim.service.QuizRunService;

@RequiredArgsConstructor
@Component
public class QuizActionListener implements ApplicationListener<QuizActionEvent> {
    private final QuizRunService runService;

    @Getter
    @Setter
    private Student student;

    @SneakyThrows
    @Override
    public void onApplicationEvent(QuizActionEvent event) {
        QuizAction quizAction = runService.runQuiz();
        event.setQuizAction(quizAction);
    }
}
