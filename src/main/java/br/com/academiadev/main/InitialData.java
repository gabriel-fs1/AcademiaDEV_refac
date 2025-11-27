package br.com.academiadev.main;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.*;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;

public class InitialData {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public InitialData(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public void populate() {
        System.out.println("Start seeding data...");

        courseRepository.save(new Course("Java for Beginners", "Intro to Java", "Nelio Alves", 40, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE));
        courseRepository.save(new Course("Advanced Spring Boot", "Microservices", "Michelli Brito", 60, DifficultyLevel.ADVANCED, CourseStatus.ACTIVE));
        courseRepository.save(new Course("Clean Architecture", "Architecture Patterns", "Otávio Lemos", 20, DifficultyLevel.INTERMEDIATE, CourseStatus.ACTIVE));
        courseRepository.save(new Course("Legacy Cobol", "Old systems", "Dinossauro", 100, DifficultyLevel.ADVANCED, CourseStatus.INACTIVE)); // Inativo

        userRepository.save(new Admin("Admin User", "admin@academiadev.com"));

        Student s1 = new Student("Gabriel Feitoza", "gabriel@gmail.com", new PremiumPlan());
        Student s2 = new Student("João da Silva", "joao@gmail.com", new BasicPlan());
        Student s3 = new Student("Maria Souza", "maria@gmail.com", new BasicPlan());

        Course javaCourse = courseRepository.findByTitle("Java for Beginners").get();
        s2.enroll(javaCourse);

        userRepository.save(s1);
        userRepository.save(s2);
        userRepository.save(s3);

        System.out.println("Data seeding completed successfully.");
    }
}
