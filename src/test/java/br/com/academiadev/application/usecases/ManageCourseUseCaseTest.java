package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManageCourseUseCaseTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private ManageCourseUseCase useCase;

    @Test
    @DisplayName("Deve criar um curso novo se o título for único")
    void shouldCreateCourseSuccessfully() {
 
        Course newCourse = new Course("Java", "Basic Java", "Prof", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);

        when(courseRepository.findByTitle("Java")).thenReturn(Optional.empty());

        useCase.createCourse(newCourse);

        verify(courseRepository, times(1)).save(newCourse);
    }

    @Test
    @DisplayName("Não deve criar curso com título duplicado")
    void shouldNotCreateDuplicateCourse() {

        Course newCourse = new Course("Java", "Basic Java", "Prof", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);

        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(newCourse));

        assertThrows(RuntimeException.class, () -> useCase.createCourse(newCourse));

        verify(courseRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve alternar status do curso (Toggle)")
    void shouldToggleCourseStatus() {

        Course existingCourse = new Course("Java", "Basic", "Prof", 10, DifficultyLevel.BEGINNER, CourseStatus.INACTIVE);
        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(existingCourse));

        useCase.toggleCourseStatus("Java");

        assertEquals(CourseStatus.ACTIVE, existingCourse.getStatus());
        verify(courseRepository, times(1)).save(existingCourse);
    }
}
