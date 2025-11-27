package br.com.academiadev.application.usecases.student;

import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;

public class CancelEnrollmentUseCase {

    private final EnrollmentRepository enrollmentRepository;

    public CancelEnrollmentUseCase(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(Student student, String courseTitle) {
        Enrollment enrollment = enrollmentRepository.findByStudent(student).stream()
                .filter(e -> e.getCourse().getTitle().equalsIgnoreCase(courseTitle))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada para o curso: " + courseTitle));

        enrollmentRepository.delete(enrollment);

        student.cancelEnrollment(courseTitle);
    }
}
