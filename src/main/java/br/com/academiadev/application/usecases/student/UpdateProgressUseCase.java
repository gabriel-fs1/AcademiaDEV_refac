package br.com.academiadev.application.usecases.student;

import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;

public class UpdateProgressUseCase {

    private final EnrollmentRepository enrollmentRepository;

    public UpdateProgressUseCase(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(Student student, String courseTitle, int newProgress) {
        Enrollment enrollment = enrollmentRepository.findByStudent(student).stream()
                .filter(e -> e.getCourse().getTitle().equalsIgnoreCase(courseTitle))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Você não está matriculado no curso: " + courseTitle));

        enrollment.updateProgress(newProgress);

        enrollmentRepository.save(enrollment);
    }
}
