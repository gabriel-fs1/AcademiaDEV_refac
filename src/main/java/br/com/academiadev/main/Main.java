package br.com.academiadev.main;

import br.com.academiadev.application.repositories.*;
import br.com.academiadev.application.usecases.admin.GenerateReportsUseCase;
import br.com.academiadev.application.usecases.admin.ProcessTicketUseCase;
import br.com.academiadev.application.usecases.courses.*;
import br.com.academiadev.application.usecases.student.*;
import br.com.academiadev.infrastructure.persistence.*;
import br.com.academiadev.infrastructure.ui.ConsoleController;

public class Main {
    public static void main(String[] args) {

        CourseRepository courseRepo = new InMemoryCourseRepository();
        UserRepository userRepo = new InMemoryUserRepository();
        StudentRepository studentRepo = new InMemoryStudentRepository();
        TicketRepository ticketRepo = new InMemoryTicketRepository();
        EnrollmentRepository enrollRepo = new InMemoryEnrollmentRepository();

        ListCourseUseCase listCourseUseCase = new ListCourseUseCase(courseRepo);
        ListStudentsUseCase listStudentsUseCase = new ListStudentsUseCase(studentRepo);
        ProcessTicketUseCase processTicketUseCase = new ProcessTicketUseCase(ticketRepo);
        ActivateCourseUseCase activateCourseUseCase = new ActivateCourseUseCase(courseRepo);
        InactivateCourseUseCase inactivateCourseUseCase = new InactivateCourseUseCase(courseRepo);
        ChangeStudentPlanUseCase changeStudentPlanUseCase = new ChangeStudentPlanUseCase(studentRepo);
        GenerateReportsUseCase generateReportsUseCase = new GenerateReportsUseCase(courseRepo, studentRepo);
        ListAvailableCoursesUseCase listAvailableCoursesUseCase = new ListAvailableCoursesUseCase(courseRepo);
        ListEnrollStudentUseCase listEnrollStudentUseCase = new ListEnrollStudentUseCase(enrollRepo);
        EnrollStudentUseCase enrollStudentUseCase = new EnrollStudentUseCase(userRepo, courseRepo, enrollRepo);
        OpenTicketUseCase openTicketUseCase = new OpenTicketUseCase(ticketRepo);
        UpdateProgressUseCase updateProgressUseCase = new UpdateProgressUseCase(enrollRepo);
        CancelEnrollmentUseCase cancelEnrollmentUseCase = new CancelEnrollmentUseCase(enrollRepo);
        SearchCourseTitleUseCase searchCourseTitleUseCase = new SearchCourseTitleUseCase(courseRepo);
        SearchStudentUseCase searchStudentUseCase = new SearchStudentUseCase(studentRepo);

        InitialData initialData = new InitialData(courseRepo, userRepo, studentRepo, ticketRepo, enrollRepo);
        initialData.populate();

        ConsoleController controller = new ConsoleController(userRepo, listCourseUseCase, listStudentsUseCase,
                processTicketUseCase, activateCourseUseCase, inactivateCourseUseCase, changeStudentPlanUseCase,
                generateReportsUseCase, listAvailableCoursesUseCase, listEnrollStudentUseCase, enrollStudentUseCase,
                openTicketUseCase, updateProgressUseCase, cancelEnrollmentUseCase, searchCourseTitleUseCase,
                searchStudentUseCase);
        controller.start();
    }
}
