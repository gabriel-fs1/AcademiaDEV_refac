package br.com.academiadev.application.usecases.courses;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.CourseStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ListAvailableCoursesUseCase {

    private final CourseRepository repository;

    public ListAvailableCoursesUseCase(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> execute() {
        return repository.findAll().stream()
                .filter(c -> c.getStatus() == CourseStatus.ACTIVE)
                .collect(Collectors.toList());
    }
}
