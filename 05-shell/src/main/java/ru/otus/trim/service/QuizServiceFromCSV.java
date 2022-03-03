package ru.otus.trim.service;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import ru.otus.trim.config.QuizConfig;
import ru.otus.trim.dao.QuizDao;
import ru.otus.trim.domain.Answer;
import ru.otus.trim.domain.Question;
import ru.otus.trim.domain.Quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuizServiceFromCSV implements QuizService {
    private final ArrayList<QuizDao> questions = new ArrayList<> ();
    private String fileName;
    public QuizServiceFromCSV(QuizConfig cfg){
        setFileName (cfg.getFileName());
    }

    public static Question parseFromCSV (String line){
        String [] tokens = line.split(",");
        String [] ans = new String [tokens.length-1];
        System.arraycopy(tokens,1, ans, 0, ans.length);

        List<Answer> answers = new ArrayList<>();
        for (String a : ans){
            boolean right = a.length() == 0 || a.startsWith("*");
            answers.add(new Answer(right ? a.substring(1) : a, right));
        }
        return new Question(tokens [0], answers, line.endsWith(","));
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;

    }

    @Override
    public Quiz readQuiz(){
        final ArrayList<Question> questions = new ArrayList<> ();

        //System.out.println("FILENAME " + fileName);
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:"+fileName);

        try (BufferedReader is = new BufferedReader(new InputStreamReader(resource.getInputStream())))
        {
            String q;
            while ((q = is.readLine()) != null){
                Question question = parseFromCSV(q);
                //System.out.println("----------------------------");
                questions.add(question);
            }

            //String [] ret = "".split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Quiz(questions);
    }
}
