package com.example.gradetracker_teamk.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "COURSES_TABLE",
        foreignKeys = {@ForeignKey(
        entity = User.class,
        parentColumns = "userId",
        childColumns = "userId")
})
public class Course {
    private int userId;
    private String courseName;
    private String subject;
    private String location;
    private String time;
    private String grade;
    private String instructor;
    private String description;
    // Primary Key auto-gen
    @PrimaryKey (autoGenerate = true)
    private int courseId;

    public Course(String courseName, String subject, String location, String time, String instructor, String description) {
        this.courseName = courseName;
        this.subject = subject;
        this.location = location;
        this.time = time;
        this.instructor = instructor;
        this.description = description;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
