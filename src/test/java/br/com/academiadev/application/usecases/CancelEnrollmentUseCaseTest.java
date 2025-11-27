package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.application.usecases.student.CancelEnrollmentUseCase;
import br.com.academiadev.domain.entities.BasicPlan;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelEnrollmentUseCaseTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private CancelEnrollmentUseCase useCase;

    @Test
    @DisplayName("Deve cancelar matrícula com sucesso removendo do repositório e do aluno")
    void shouldCancelEnrollmentSuccessfully() {
      
        String courseTitle = "Java for Beginners";
        Student student = new Student("Gabriel", "gabriel@email.com", new BasicPlan());
        Course course = new Course(courseTitle, "Desc", "Prof", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);

        student.enroll(course);
        Enrollment enrollment = student.getAllEnrollments().get(0);

        List<Enrollment> dbEnrollments = new ArrayList<>();
        dbEnrollments.add(enrollment);

        when(enrollmentRepository.findByStudent(student)).thenReturn(dbEnrollments);

        useCase.execute(student, courseTitle);

        verify(enrollmentRepository, times(1)).delete(enrollment);

        assertTrue(student.getAllEnrollments().isEmpty(), "A lista de matrículas do aluno deve estar vazia");
    }

    @Test
    @DisplayName("Deve lançar erro se tentar cancelar uma matrícula que não existe")
    void shouldThrowExceptionWhenEnrollmentNotFound() {

        Student student = new Student("Gabriel", "gabriel@email.com", new BasicPlan());

        when(enrollmentRepository.findByStudent(student)).thenReturn(new ArrayList<>());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            useCase.execute(student, "Curso Fantasma");
        });

        assertEquals("Matrícula não encontrada para o curso: Curso Fantasma", exception.getMessage());

        verify(enrollmentRepository, never()).delete(any());
    }
}
