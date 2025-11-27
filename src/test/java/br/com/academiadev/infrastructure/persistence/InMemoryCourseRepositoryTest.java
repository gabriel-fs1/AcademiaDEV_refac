package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryCourseRepositoryTest {

    private InMemoryCourseRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryCourseRepository();
    }

    @Test
    @DisplayName("Deve salvar e recuperar um curso por título")
    void shouldSaveAndFindCourse() {
        Course course = new Course("Java", "Basic", "Prof", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);

        repository.save(course);

        Optional<Course> found = repository.findByTitle("Java");
        assertTrue(found.isPresent());
        assertEquals("Java", found.get().getTitle());
    }

    @Test
    @DisplayName("Deve retornar vazio se curso não existe")
    void shouldReturnEmptyIfNotFound() {
        Optional<Course> found = repository.findByTitle("Python");
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Deve listar todos os cursos salvos")
    void shouldFindAllCourses() {
        repository.save(new Course("Java", "B", "P", 10, null, null));
        repository.save(new Course("Python", "B", "P", 10, null, null));

        List<Course> all = repository.findAll();
        assertEquals(2, all.size());
    }
}
