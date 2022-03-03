package ru.otus.trim.service;

import org.springframework.stereotype.Service;
import ru.otus.trim.dao.QuizDao;

@Service
public class QuizOutServiceToConsole implements QuizOutService {

    final QuizDao quizDao;

    public QuizOutServiceToConsole(QuizDao dao){
        this.quizDao = dao;
    }

    @Override
    public void outQuiz() {
        quizDao.getQuiz().getQuestions().forEach(t -> System.out.println(t.question));
    }
}
