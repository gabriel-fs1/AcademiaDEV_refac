package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.User;

public class EnrollStudentUseCase {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollStudentUseCase(UserRepository userRepository,
                                CourseRepository courseRepository,
                                EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(String email, String courseTitle) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        if (!(user instanceof Student)) {
            throw new RuntimeException("Only students can enroll in courses");
        }
        Student student = (Student) user;

        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseTitle));

        boolean enrolled = student.enroll(course);

        if (!enrolled) {
            throw new RuntimeException("Student cannot enroll in this course (Check Plan limit or Course status)");
        }

        Enrollment newEnrollment = student.getAllEnrollments().stream()
                .filter(e -> e.getCourse().getTitle().equals(course.getTitle()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Enrollment failed internally"));

        enrollmentRepository.save(newEnrollment);
    }
}
