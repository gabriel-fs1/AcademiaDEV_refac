package br.com.academiadev.domain;

import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    @DisplayName("Não deve criar curso com título vazio")
    void shouldThrowExceptionIfTitleIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Course("", "Desc", "Prof", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        });
    }

    @Test
    @DisplayName("Deve identificar corretamente se o curso está Ativo")
    void shouldIdentifyActiveStatus() {
        Course activeCourse = new Course("Java", "D", "P", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Course inactiveCourse = new Course("Python", "D", "P", 10, DifficultyLevel.BEGINNER, CourseStatus.INACTIVE);

        assertTrue(activeCourse.isActive());
        assertFalse(inactiveCourse.isActive());
    }
}
