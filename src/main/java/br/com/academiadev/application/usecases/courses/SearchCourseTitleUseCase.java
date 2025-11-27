package br.com.academiadev.application.usecases.courses;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.exceptions.BusinessException;

public class SearchCourseTitleUseCase {
    private final CourseRepository courseRepository;

    public SearchCourseTitleUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course execute(String title) {
        return courseRepository.findByTitle(title)
                .orElseThrow(() -> new BusinessException("Curso não encontrado com o título: " + title));
    }

}
