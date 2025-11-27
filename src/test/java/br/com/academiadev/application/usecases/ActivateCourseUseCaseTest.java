package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.usecases.courses.ActivateCourseUseCase;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivateCourseUseCaseTest {

    @Mock // Cria um repositório "falso"
    private CourseRepository repository;

    @InjectMocks // Injeta o mock no nosso UseCase
    private ActivateCourseUseCase useCase;

    @Test
    @DisplayName("Deve ativar um curso existente com sucesso")
    void shouldActivateExistingCourse() {

        String title = "Java Legacy";

        Course course = new Course(title, "Desc", "Prof", 10, DifficultyLevel.BEGINNER, CourseStatus.INACTIVE);

        when(repository.findByTitle(title)).thenReturn(Optional.of(course));

        useCase.execute(title);

        assertEquals(CourseStatus.ACTIVE, course.getStatus());

        verify(repository).save(course);
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar ativar curso inexistente")
    void shouldThrowExceptionWhenCourseNotFound() {

        String title = "Ghost Course";

        when(repository.findByTitle(title)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> useCase.execute(title));

        verify(repository, never()).save(any());
    }
}
