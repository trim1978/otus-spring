package ru.otus.trim.events;

import ru.otus.trim.domain.QuizAction;

public interface QuizActionPublisher {
    QuizAction runQuiz();
}
