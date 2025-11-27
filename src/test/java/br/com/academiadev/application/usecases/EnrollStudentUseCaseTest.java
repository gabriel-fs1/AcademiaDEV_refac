package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.BasicPlan;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollStudentUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollStudentUseCase useCase;

    @Test
    @DisplayName("Deve matricular aluno com sucesso quando tudo estiver ok")
    void shouldEnrollStudentSuccessfully() {

        Student student = new Student("Gabriel", "gabriel@email.com", new BasicPlan());
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);

        when(userRepository.findByEmail("gabriel@email.com")).thenReturn(Optional.of(student));
        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(course));

        assertDoesNotThrow(() -> useCase.execute("gabriel@email.com", "Java"));
 
        verify(enrollmentRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro se curso não existe")
    void shouldThrowErrorIfCourseNotFound() {
        when(userRepository.findByEmail("gabriel@email.com")).thenReturn(Optional.of(new Student("G", "g", new BasicPlan())));
        when(courseRepository.findByTitle("Java")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> useCase.execute("gabriel@email.com", "Java"));
    }
}
