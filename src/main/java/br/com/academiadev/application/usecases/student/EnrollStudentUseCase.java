package br.com.academiadev.application.usecases.student;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.User;

public class EnrollStudentUseCase {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollStudentUseCase(UserRepository userRepository,
                                CourseRepository courseRepository,
                                EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(String email, String courseTitle) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + email));

        if (!(user instanceof Student)) {
            throw new RuntimeException("Apenas alunos podem se matricular.");
        }
        Student student = (Student) user;

        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado: " + courseTitle));

        boolean success = student.enroll(course);

        if (!success) {
            throw new RuntimeException("Não foi possível realizar a matrícula. Verifique se você já possui este curso, se ele está ativo ou se atingiu o limite do seu plano.");
        }

        Enrollment newEnrollment = student.getAllEnrollments().stream()
                .filter(e -> e.getCourse().getTitle().equals(course.getTitle()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Erro interno ao recuperar matrícula."));

        enrollmentRepository.save(newEnrollment);
    }
}
