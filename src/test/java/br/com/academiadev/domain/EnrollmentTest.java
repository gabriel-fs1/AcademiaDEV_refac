package br.com.academiadev.domain;

import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.BasicPlan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {

    @Test
    @DisplayName("Deve iniciar com progresso 0%")
    void shouldStartWithZeroProgress() {
        Student student = new Student("Gabriel", "g@email.com", new BasicPlan());
        Course course = new Course("Java", "Desc", "Prof", 10, null, null);

        Enrollment enrollment = new Enrollment(student, course);

        assertEquals(0, enrollment.getProgressPercent());
    }

    @Test
    @DisplayName("Não deve permitir progresso maior que 100%")
    void shouldNotAllowProgressGreaterThan100() {
        Student student = new Student("Gabriel", "g@email.com", new BasicPlan());
        Course course = new Course("Java", "Desc", "Prof", 10, null, null);
        Enrollment enrollment = new Enrollment(student, course);

        // A regra diz "0 a 100", então valores fora disso são inválidos
        assertThrows(IllegalArgumentException.class, () -> {
            enrollment.updateProgress(101);
        });
    }

    @Test
    @DisplayName("Não deve permitir progresso negativo")
    void shouldNotAllowNegativeProgress() {
        Student student = new Student("Gabriel", "g@email.com", new BasicPlan());
        Course course = new Course("Java", "Desc", "Prof", 10, null, null);
        Enrollment enrollment = new Enrollment(student, course);

        assertThrows(IllegalArgumentException.class, () -> {
            enrollment.updateProgress(-1);
        });
    }
}
