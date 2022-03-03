package ru.otus.trim.service;

import org.springframework.stereotype.Service;
import ru.otus.trim.config.QuizConfig;

@Service
public class QuizServiceExample {
    public final String quizFile;
    public final int enough;
    public final String locale;

    public QuizServiceExample(QuizConfig quizConfig){
        this.quizFile = quizConfig.getFileName();
        this.enough = quizConfig.getEnough();
        this.locale = quizConfig.getLocale();
    }

    public String outConfigData() {
        return quizFile + " " + enough + " " + locale;
    }

}
