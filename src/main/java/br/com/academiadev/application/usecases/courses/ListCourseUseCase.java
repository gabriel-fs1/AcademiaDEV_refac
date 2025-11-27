package br.com.academiadev.application.usecases.courses;

import java.util.List;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;

public class ListCourseUseCase {
    private final CourseRepository courseRepository;
    public ListCourseUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> execute() {
        return courseRepository.findAll();
    }
}
