package br.com.academiadev.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.academiadev.domain.entities.BasicPlan;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;

public class StudentTest {

    @Test
    @DisplayName("Deve permitir cancelar matrícula e liberar vaga no plano")
    void shouldCancelEnrollmentAndFreeSlot() {
        Student student = new Student("Gabriel", "g@mail.com", new BasicPlan()); 
        Course c1 = new Course("Java", "D", "P", 10, DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);

        student.enroll(c1);
        assertEquals(1, student.getAllEnrollments().size());

        student.cancelEnrollment("Java");

        assertTrue(student.getAllEnrollments().isEmpty());
        assertTrue(student.getPlan().canEnroll(0));
    }
}
