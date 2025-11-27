package br.com.academiadev.application.usecases.student;

import java.util.List;

import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;

public class ListEnrollStudentUseCase {
    private final EnrollmentRepository repository;

    public ListEnrollStudentUseCase(EnrollmentRepository repository) {
        this.repository = repository;
    }

    public List<Enrollment> execute(Student student) {
        return repository.findByStudent(student);
    }
}
