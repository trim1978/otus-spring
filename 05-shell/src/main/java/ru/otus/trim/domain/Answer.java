package ru.otus.trim.domain;

public class Answer {
    public final boolean right;
    public final String answerText;

    public Answer(String answerText) {
        this (answerText, true);
    }

    public Answer(String answerText, boolean right) {
        this.right = right;
        this.answerText = answerText;
    }

//    public boolean isCorrectAnswer (){
//        return answerText.startsWith("*");
//    }
//
//    public String getAnswerText (){
//        return answerText.startsWith("*") ? answerText.substring(1).trim() : answerText;
//    }
}
