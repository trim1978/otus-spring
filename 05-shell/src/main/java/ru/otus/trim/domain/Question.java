package ru.otus.trim.domain;

import java.util.ArrayList;
import java.util.List;

public class Question{
    public final String question;

    private final List<Answer> answers;

    public final boolean neewFreeAnswer;

    public Question(String question, List<Answer> answers, boolean neewFreeAnswer) {
        this.question = question;
        this.answers = answers;
        this.neewFreeAnswer = neewFreeAnswer;
    }

    public List<Answer> getAnswers (){
        return new ArrayList<> (answers);
    }

//    public void out (PrintStream ps){
//        ps.println(question +" ?");
//    }

}
