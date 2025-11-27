package br.com.academiadev.application.usecases.courses;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.CourseStatus;

public class ActivateCourseUseCase {
    private final CourseRepository repository;
    public ActivateCourseUseCase(CourseRepository repository) { this.repository = repository; }

    public void execute(String title) {
        Course course = repository.findByTitle(title).orElseThrow();
        course.setStatus(CourseStatus.ACTIVE);
        repository.save(course);
    }
}
