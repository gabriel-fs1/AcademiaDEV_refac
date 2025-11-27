package br.com.academiadev.application.usecases.courses;

import java.util.Optional;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.exceptions.BusinessException;

public class SearchCourseDiffUseCase {
    private final CourseRepository courseRepository;

    public SearchCourseDiffUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Optional<Course> execute(CourseStatus difficulty) {
        Course course = courseRepository.findByDifficulty(difficulty)
                .orElseThrow(() -> new BusinessException("Course not found with difficulty: " + difficulty));

        return Optional.of(course);
    }


}
