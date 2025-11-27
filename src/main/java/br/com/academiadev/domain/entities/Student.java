package br.com.academiadev.domain.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends User {
    private SubscriptionPlan plan;
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student(String name, String email, SubscriptionPlan plan) {
        super(name, email);
        this.plan = plan;
    }

    public boolean enroll(Course course) {

        if (!course.isActive()) {
            return false;
        }

        boolean alreadyEnrolled = enrollments.stream()
            .anyMatch(e -> e.getCourse().getTitle().equals(course.getTitle()));

        if (alreadyEnrolled) {
            return false;
        }

        if (plan.canEnroll(enrollments.size())) {
            enrollments.add(new Enrollment(this, course));
            return true;
        }

        return false;
    }

    public List<Enrollment> getAllEnrollments() {
        return Collections.unmodifiableList(enrollments);
    }

    public SubscriptionPlan getPlan() { return plan; }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }

    public void cancelEnrollment(String courseTitle) {
        enrollments.removeIf(e -> e.getCourse().getTitle().equalsIgnoreCase(courseTitle));
    }
}
