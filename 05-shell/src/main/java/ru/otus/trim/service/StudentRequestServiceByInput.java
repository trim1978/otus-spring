package ru.otus.trim.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.trim.config.QuizConfig;
import ru.otus.trim.domain.Student;

import java.util.Locale;
import java.util.Scanner;

@Service
public class StudentRequestServiceByInput implements StudentRequestService{
    private final MessageSource msg;
    private final Locale locale;
    private final IOService ioService;

    public StudentRequestServiceByInput(QuizConfig cfg, MessageSource msg, IOService ioService) {
        this.msg = msg;
        this.locale = Locale.forLanguageTag(cfg.getLocale());
        this.ioService = ioService;
    }

    @Override
    public Student requestStudent() {
        ioService.out(msg.getMessage(
                "quiz.enter.firstname",
                null,
                locale
        ) + ": ");

        String firstName = ioService.readString();
        ioService.out(msg.getMessage(
                "quiz.enter.lastname",
                null,
                locale
        ) + ": ");

        String lastName = ioService.readString();
        ioService.out(msg.getMessage(
                "quiz.enter.welcome",
                new String[] {firstName, lastName},
                locale
        ) + " !");
        return new Student(firstName, lastName);
    }
}
