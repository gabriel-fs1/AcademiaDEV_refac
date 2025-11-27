package br.com.academiadev.main;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.application.repositories.TicketQueue;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.infrastructure.persistence.InMemoryCourseRepository;
import br.com.academiadev.infrastructure.persistence.InMemoryEnrollmentRepository;
import br.com.academiadev.infrastructure.persistence.InMemoryTicketQueue;
import br.com.academiadev.infrastructure.persistence.InMemoryUserRepository;
import br.com.academiadev.infrastructure.ui.ConsoleController;
import br.com.academiadev.infrastructure.ui.ConsoleView;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepo = new InMemoryUserRepository();
        CourseRepository courseRepo = new InMemoryCourseRepository();
        EnrollmentRepository enrollmentRepo = new InMemoryEnrollmentRepository();
        TicketQueue ticketQueue = new InMemoryTicketQueue();

        InitialData initialData = new InitialData(courseRepo, userRepo);
        initialData.populate();

        ConsoleView view = new ConsoleView();

        ConsoleController controller = new ConsoleController(
            view,
            userRepo,
            courseRepo,
            enrollmentRepo,
            ticketQueue
        );

        System.out.println("\nSistema inicializado com sucesso!");
        controller.start();
    }
}
