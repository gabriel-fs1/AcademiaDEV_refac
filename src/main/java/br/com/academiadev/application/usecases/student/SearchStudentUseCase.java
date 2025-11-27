package br.com.academiadev.application.usecases.student;
import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.exceptions.BusinessException;

public class SearchStudentUseCase {
    private final StudentRepository repository;

    public SearchStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public Student execute(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Student not found"));
    }
}
