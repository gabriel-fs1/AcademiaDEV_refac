package br.com.academiadev.main;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.application.repositories.TicketRepository;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.*;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;

public class InitialData {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TicketRepository ticketRepository;
    private final EnrollmentRepository enrollmentRepository;

    public InitialData(CourseRepository courseRepository,
                       UserRepository userRepository,
                       StudentRepository studentRepository,
                       TicketRepository ticketRepository,
                       EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.ticketRepository = ticketRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void populate() {
        System.out.println("Start seeding data...");

        Course javaCourse = new Course("Java for Beginners", "Intro to Java", "Nelio Alves", 40, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        courseRepository.save(javaCourse);

        courseRepository.save(new Course("Advanced Spring Boot", "Microservices", "Michelli Brito", 60, DifficultyLevel.ADVANCED, CourseStatus.ACTIVE));
        courseRepository.save(new Course("Clean Architecture", "Architecture Patterns", "Otávio Lemos", 20, DifficultyLevel.INTERMEDIATE, CourseStatus.ACTIVE));
        courseRepository.save(new Course("Legacy Cobol", "Old systems", "Dinossauro", 100, DifficultyLevel.ADVANCED, CourseStatus.INACTIVE));
        courseRepository.save(new Course("JavaScript Basics", "Intro to JS", "Fulano de Tal", 30, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE));

        userRepository.save(new Admin("Admin User", "admin@academiadev.com"));

        Student s1 = new Student("Gabriel Feitoza", "gabriel@gmail.com", new PremiumPlan());
        Student s2 = new Student("João da Silva", "joao@gmail.com", new BasicPlan());
        Student s3 = new Student("Maria Souza", "maria@gmail.com", new BasicPlan());

        s2.enroll(javaCourse);

        Enrollment enrollmentJoao = s2.getAllEnrollments().stream()
                .filter(e -> e.getCourse().getTitle().equals(javaCourse.getTitle()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Erro ao criar dados iniciais de matrícula"));

        enrollmentRepository.save(enrollmentJoao);

        userRepository.save(s1);
        userRepository.save(s2);
        userRepository.save(s3);

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);

        SupportTicket t1 = new SupportTicket("Erro no Login", "Não consigo acessar minha conta", s2);
        ticketRepository.add(t1);

        System.out.println("Data seeding completed successfully.");
    }
}
