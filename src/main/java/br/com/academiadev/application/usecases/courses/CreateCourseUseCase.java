package br.com.academiadev.application.usecases.courses;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;

public class CreateCourseUseCase {
    private final CourseRepository repository;
    public CreateCourseUseCase(CourseRepository repository) { this.repository = repository; }

    public void execute(Course course) {
        if (repository.findByTitle(course.getTitle()).isPresent())
            throw new RuntimeException("Course with title " + course.getTitle() + " already exists.");
        repository.save(course);
    }
}
