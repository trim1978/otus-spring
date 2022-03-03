package ru.otus.trim.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class QuizAction {
    public final Quiz quiz;

    private final HashMap<Question, Set<Answer>> answers = new HashMap<> ();

    public QuizAction(Quiz quiz) {
        this.quiz = quiz;
        quiz.getQuestions().forEach(t -> answers.put(t, null));
    }

    public boolean isComplete (){
        return answers.values().stream().filter(t -> t != null && t.size() > 0).count() == quiz.getQuestions().size();
    }

    public void addAnswer (Question question, Answer answer){
        if (answers.containsKey(question)){
            Set<Answer> set;
            set = answers.computeIfAbsent(question, k -> new HashSet<>());
            set.add(answer);
        }
    }

    public void setAnswers (Question question, Set<Answer> answer){
        if (this.answers.containsKey(question)){
            this.answers.put(question, answer);
        }
    }

    public boolean isRightAnsweredQuestion (Question question){
        Set<Answer> set = answers.get(question);
        if (set != null){
            int rightCount = 0;
            for (Answer a : question.getAnswers()){
                if (a.right){
                    rightCount++;
                    if (!set.contains(a)){
                        return false;
                    }
                }
            }
            return rightCount == set.size();
        }
        return false;
    }

    public int getRightAnsweredQuestionsCount (){
        return (int)quiz.getQuestions().stream().filter(this::isRightAnsweredQuestion).count();
    }
}
