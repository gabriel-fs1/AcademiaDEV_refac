package br.com.academiadev.infrastructure.utils;

import br.com.academiadev.domain.entities.BasicPlan;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenericCsvExporterTest {

    @Test
    @DisplayName("Deve exportar colunas simples de uma lista de Cursos")
    void shouldExportSimpleFields() {
     
        Course c1 = new Course("Java Intro", "Desc", "Nelio", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Course c2 = new Course("Spring Boot", "Desc", "Michelli", 20, DifficultyLevel.ADVANCED, CourseStatus.ACTIVE);

        List<Course> data = Arrays.asList(c1, c2);
        List<String> columns = Arrays.asList("title", "instructorName", "durationInHours");

        String csvResult = GenericCsvExporter.exportToCsv(data, columns);
        String[] lines = csvResult.split("\n");

        assertEquals("title;instructorName;durationInHours", lines[0]);
        assertEquals("Java Intro;Nelio;10", lines[1]);
        assertEquals("Spring Boot;Michelli;20", lines[2]);
    }

    @Test
    @DisplayName("Deve exportar campos herdados (Student -> User)")
    void shouldExportInheritedFields() {
        Student s1 = new Student("Gabriel", "gabriel@test.com", new BasicPlan());
        List<Student> data = Collections.singletonList(s1);
        List<String> columns = Arrays.asList("name", "email");

        String csvResult = GenericCsvExporter.exportToCsv(data, columns);
        String[] lines = csvResult.split("\n");

        assertEquals("name;email", lines[0]);
        assertEquals("Gabriel;gabriel@test.com", lines[1]);
    }

    @Test
    @DisplayName("Deve retornar mensagem amigável se lista vazia")
    void shouldHandleEmptyList() {
        String result = GenericCsvExporter.exportToCsv(Collections.emptyList(), Arrays.asList("title"));
        assertEquals("No data to export.", result);
    }

    @Test
    @DisplayName("Deve preencher com ERROR se pedir campo que não existe")
    void shouldHandleNonExistentField() {
        Course c1 = new Course("Java", "Desc", "Prof", 10, null, null);
        List<Course> data = Collections.singletonList(c1);

        String result = GenericCsvExporter.exportToCsv(data, Arrays.asList("title", "salario"));

        assertTrue(result.contains("Java;ERROR"));
    }
}
