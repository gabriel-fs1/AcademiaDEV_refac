package br.com.academiadev.application.usecases.admin;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;

import java.util.*;
import java.util.stream.Collectors;

public class GenerateReportsUseCase {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public GenerateReportsUseCase(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<Course> getCoursesByDifficulty(DifficultyLevel level) {
        return courseRepository.findAll().stream()
                .filter(c -> c.getDifficulty() == level)
                .sorted(Comparator.comparing(Course::getTitle))
                .collect(Collectors.toList());
    }

    public Set<String> getActiveInstructors() {
        return courseRepository.findAll().stream()
                .filter(c -> c.getStatus() == CourseStatus.ACTIVE)
                .map(Course::getInstructorName)
                .collect(Collectors.toSet());
    }

    public Map<String, List<Student>> getStudentsByPlan() {
        return studentRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        student -> student.getPlan().getTypeName()
                ));
    }

    public double getGeneralProgressAverage() {
        List<Student> students = studentRepository.findAll();

        List<Enrollment> allEnrollments = students.stream()
                .flatMap(s -> s.getAllEnrollments().stream())
                .collect(Collectors.toList());

        if (allEnrollments.isEmpty()) return 0.0;

        return allEnrollments.stream()
                .mapToInt(Enrollment::getProgressPercent)
                .average()
                .orElse(0.0);
    }

    public Optional<Student> getTopStudent() {
        return studentRepository.findAll().stream()
                .max(Comparator.comparingInt(s -> s.getAllEnrollments().size()));
    }
}
