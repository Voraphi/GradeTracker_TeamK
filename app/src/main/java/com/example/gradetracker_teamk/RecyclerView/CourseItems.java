package com.example.gradetracker_teamk.RecyclerView;

public class CourseItems {
    String courseName;
    String location;
    String grade;

    public CourseItems(String courseName, String location, String grade) {
        this.courseName = courseName;
        this.location = location;
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
