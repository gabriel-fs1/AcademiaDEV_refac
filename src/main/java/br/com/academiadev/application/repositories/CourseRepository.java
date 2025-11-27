package br.com.academiadev.application.repositories;

import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.CourseStatus;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    void save(Course course);
    Optional<Course> findByDifficulty(CourseStatus difficulty);
    Optional<Course> findByTitle(String title);
    List<Course> findAll();
}
