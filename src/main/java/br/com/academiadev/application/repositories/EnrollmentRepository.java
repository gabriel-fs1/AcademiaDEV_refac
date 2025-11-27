package br.com.academiadev.application.repositories;

import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;
import java.util.List;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    List<Enrollment> findByStudent(Student student);
    boolean existsByStudentAndCourse(Student student, String courseTitle);
    void delete(Enrollment enrollment);
}
