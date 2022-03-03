package ru.otus.trim.service;

import org.springframework.stereotype.Service;
import ru.otus.trim.config.QuizConfig;
import ru.otus.trim.domain.QuizAction;

@Service
public class QuizCheckServiceFromConfig implements QuizCheckService{
    private final int checkCriterion;

    public QuizCheckServiceFromConfig(QuizConfig cfg) {
        this.checkCriterion = cfg.getEnough();
    }

    @Override
    public boolean checkQuizAction(QuizAction quizAction) {
        return quizAction.isComplete() && quizAction.getRightAnsweredQuestionsCount() >= checkCriterion;  }
}
