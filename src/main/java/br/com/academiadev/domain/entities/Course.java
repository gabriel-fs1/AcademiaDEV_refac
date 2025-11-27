package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;

public class Course {
    private String title;
    private String description;
    private String instructorName; 
    private int durationInHours;
    private DifficultyLevel difficulty;
    private CourseStatus status;

    public Course(String title, String description, String instructorName, int durationInHours, DifficultyLevel difficulty, CourseStatus status) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be empty");
        this.title = title;
        this.description = description;
        this.instructorName = instructorName;
        this.durationInHours = durationInHours;
        this.difficulty = difficulty;
        this.status = status;
    }

    public boolean isActive() {
        return this.status == CourseStatus.ACTIVE;
    }

    public String getTitle() { return title; }
    public String getInstructorName() { return instructorName; }
    public DifficultyLevel getDifficulty() { return difficulty; }
    public CourseStatus getStatus() { return status; }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }
}
