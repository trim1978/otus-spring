package ru.otus.trim.shell;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.trim.config.QuizConfig;
import ru.otus.trim.domain.QuizAction;
import ru.otus.trim.domain.Student;
import ru.otus.trim.events.QuizActionPublisher;
import ru.otus.trim.service.QuizCheckService;
import ru.otus.trim.service.StudentRequestService;

@ShellComponent
@RequiredArgsConstructor
public class QuizCommandComponent {

    private final QuizActionPublisher eventsPublisher;

    private final StudentRequestService studentRequest;

    //private final QuizCheckService checkService;

    private final QuizConfig quizConfig;

    private Student student;

    @ShellMethod(value = "Start quiz", key = {"start", "run",})
    @ShellMethodAvailability(value = "isQuizStartAvailable")
    public String quizRun() {
        QuizAction quizAction = eventsPublisher.runQuiz();
        boolean complete = quizAction.isComplete() && quizAction.getRightAnsweredQuestionsCount() >= quizConfig.getEnough();
        return complete ? "done" : "fault";
    }

    @ShellMethod(value = "Enter firstname and lastname of student", key = "enter")
    public String studentRequest() {
        student = studentRequest.requestStudent();
        return "Student is ready";
    }

    @ShellMethod(value = "Set enough right answers count", key = "set_enough")
    public String setEnough(int enough) {
        quizConfig.setEnough(enough);
        return "enough is " + quizConfig.getEnough();
    }

    @ShellMethod(value = "Who is here", key = "who")
    public String getStudent() {
        return student != null ? student.firstName + " " + student.lastName : "< none >";
    }

    private Availability isQuizStartAvailable() {
        return student == null? Availability.unavailable("You must enter before"): Availability.available();
    }
}
