package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.CourseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCourseRepository implements CourseRepository {

    private final Map<String, Course> db = new HashMap<>();

    @Override
    public void save(Course course) {
        db.put(course.getTitle(), course);
    }

    @Override
    public Optional<Course> findByDifficulty(CourseStatus difficulty) {
        return db.values().stream()
                .filter(c -> c.getStatus() == difficulty)
                .findFirst();
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return Optional.ofNullable(db.get(title));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(db.values());
    }
}
