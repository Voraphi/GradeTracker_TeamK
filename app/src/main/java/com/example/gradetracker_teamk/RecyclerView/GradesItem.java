package com.example.gradetracker_teamk.RecyclerView;

public class GradesItem {
    private String course_name;
    private String grade;

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public GradesItem(String courseName, String grade) {
        this.course_name = courseName;
        this.grade = grade;
    }
}
