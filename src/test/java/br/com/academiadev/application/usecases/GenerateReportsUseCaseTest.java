package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.application.usecases.admin.GenerateReportsUseCase;
import br.com.academiadev.domain.entities.*;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateReportsUseCaseTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private GenerateReportsUseCase useCase;

    @Test
    @DisplayName("Deve filtrar cursos por dificuldade e ordenar alfabeticamente")
    void shouldFilterCoursesByDifficultyAndSort() {
    
        Course c1 = new Course("Java Basic", "Desc", "Prof A", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Course c2 = new Course("Advanced Java", "Desc", "Prof B", 10, DifficultyLevel.ADVANCED, CourseStatus.ACTIVE);
        Course c3 = new Course("Zylophone Music", "Desc", "Prof C", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Course c4 = new Course("Algorithms", "Desc", "Prof D", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);

        when(courseRepository.findAll()).thenReturn(Arrays.asList(c1, c2, c3, c4));

        List<Course> result = useCase.getCoursesByDifficulty(DifficultyLevel.BEGINNER);

        assertEquals(3, result.size());
        assertEquals("Algorithms", result.get(0).getTitle());
        assertEquals("Java Basic", result.get(1).getTitle());
        assertEquals("Zylophone Music", result.get(2).getTitle());
    }

    @Test
    @DisplayName("Deve listar instrutores únicos de cursos ativos")
    void shouldReturnUniqueActiveInstructors() {
        Course c1 = new Course("C1", "D", "Nelio", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Course c2 = new Course("C2", "D", "Nelio", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE); // Duplicado
        Course c3 = new Course("C3", "D", "Michelli", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Course c4 = new Course("C4", "D", "InativoBoy", 10, DifficultyLevel.BEGINNER, CourseStatus.INACTIVE); // Inativo

        when(courseRepository.findAll()).thenReturn(Arrays.asList(c1, c2, c3, c4));

        Set<String> instructors = useCase.getActiveInstructors();

        assertEquals(2, instructors.size());
        assertTrue(instructors.contains("Nelio"));
        assertTrue(instructors.contains("Michelli"));
        assertFalse(instructors.contains("InativoBoy"));
    }

    @Test
    @DisplayName("Deve agrupar alunos por plano de assinatura")
    void shouldGroupStudentsByPlan() {
        Student s1 = new Student("S1", "e1", new BasicPlan());
        Student s2 = new Student("S2", "e2", new BasicPlan());
        Student s3 = new Student("S3", "e3", new PremiumPlan());

        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2, s3));

        Map<String, List<Student>> grouped = useCase.getStudentsByPlan();

        assertEquals(2, grouped.size());
        assertEquals(2, grouped.get("BASIC").size());
        assertEquals(1, grouped.get("PREMIUM").size());
    }

    @Test
    @DisplayName("Deve calcular a média geral de progresso corretamente")
    void shouldCalculateGeneralProgressAverage() {

        Student s1 = new Student("S1", "e1", new BasicPlan());
        Student s2 = new Student("S2", "e2", new BasicPlan());
        Course c = new Course("C", "D", "P", 10, null, CourseStatus.ACTIVE);

        s1.enroll(c);
        s1.getAllEnrollments().get(0).updateProgress(100);

        s2.enroll(c);
        s2.getAllEnrollments().get(0).updateProgress(50);

        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        double average = useCase.getGeneralProgressAverage();

        assertEquals(75.0, average, 0.01);
    }

    @Test
    @DisplayName("Deve retornar 0 se não houver matrículas para média")
    void shouldReturnZeroAverageIfNoEnrollments() {
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(0.0, useCase.getGeneralProgressAverage());
    }

    @Test
    @DisplayName("Deve identificar o aluno com maior número de matrículas")
    void shouldFindTopStudent() {
        Student s1 = new Student("S1", "e1", new BasicPlan());
        Student s2 = new Student("TopStudent", "e2", new PremiumPlan());
        Course c = new Course("C", "D", "P", 10, null, CourseStatus.ACTIVE);

        s2.enroll(c);
        Course c2 = new Course("C2", "D", "P", 10, null, CourseStatus.ACTIVE);
        Course c3 = new Course("C3", "D", "P", 10, null, CourseStatus.ACTIVE);
        s2.enroll(c2);
        s2.enroll(c3);

        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        Optional<Student> result = useCase.getTopStudent();

        assertTrue(result.isPresent());
        assertEquals("TopStudent", result.get().getName());
    }
}
