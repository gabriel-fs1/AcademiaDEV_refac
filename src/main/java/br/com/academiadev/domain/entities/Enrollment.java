package br.com.academiadev.domain.entities;

import java.time.LocalDateTime;

public class Enrollment {
    private Student student;
    private Course course;
    private int progressPercent;
    private LocalDateTime enrollmentDate;

    public Enrollment(Student student, Course course) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and Course are required for enrollment");
        }
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDateTime.now();
        this.progressPercent = 0;
    }

    public void updateProgress(int newProgress) {
        if (newProgress < 0 || newProgress > 100) {
            throw new IllegalArgumentException("O progresso deve ser entre 0 e 100.");
        }

        if (newProgress < this.progressPercent) {
            throw new IllegalArgumentException("O progresso não pode diminuir. Atual: " + this.progressPercent + "%");
        }

        this.progressPercent = newProgress;
    }

    public int getProgressPercent() {
        return progressPercent;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }
}
