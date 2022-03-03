package ru.otus.trim.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.otus.trim.domain.Quiz;
import ru.otus.trim.service.QuizService;


@Repository("quizDao")
public class QuizDaoSimple implements QuizDao{

    public final Quiz quiz;

    public QuizDaoSimple(QuizService service) {
        this.quiz = service.readQuiz();
    }

    @Override
    public Quiz getQuiz() {
        return quiz;
    }

}
