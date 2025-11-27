package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.CourseStatus;

public class ManageCourseUseCase {

    private final CourseRepository courseRepository;

    public ManageCourseUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void createCourse(Course course) {

        if (courseRepository.findByTitle(course.getTitle()).isPresent()) {
            throw new RuntimeException("Course with title '" + course.getTitle() + "' already exists.");
        }
        courseRepository.save(course);
    }

    public void toggleCourseStatus(String courseTitle) {
        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseTitle));

        if (course.getStatus() == CourseStatus.ACTIVE) {
            course.setStatus(CourseStatus.INACTIVE);
        } else {
            course.setStatus(CourseStatus.ACTIVE);
        }

        courseRepository.save(course);
    }
}
