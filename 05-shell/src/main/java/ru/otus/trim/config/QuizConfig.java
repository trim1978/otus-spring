package ru.otus.trim.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Locale;

@ConfigurationProperties(prefix = "quiz")
@Configuration
@PropertySource("classpath:application.yml")
public class QuizConfig {

    private String file;

    private int enough;

    private String locale = "";

//    public QuizConfig (){
//        System.out.println ("CREATING");
//    }
    public String getFile() {
        return file;
    }

    public String getFileName() {
        return file + (getLocale().length() > 0 ? "_" + Locale.forLanguageTag(getLocale()) : "") + ".csv";
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getEnough() {
        return enough;
    }

    public void setEnough(int enough) {
        this.enough = enough;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
