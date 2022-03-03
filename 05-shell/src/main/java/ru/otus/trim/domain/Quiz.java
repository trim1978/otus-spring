package ru.otus.trim.domain;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private final List<Question> questions = new ArrayList<>();

    public Quiz (List<Question> questions){
        this.questions.addAll (questions);
    }

    public List<Question> getQuestions() {
        return new ArrayList<> (questions);
    }

    public QuizAction createQuizAction (){
        return new QuizAction (this);
    }
}
