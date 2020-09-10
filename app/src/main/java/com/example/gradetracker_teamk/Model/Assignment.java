package com.example.gradetracker_teamk.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assignments")
public class Assignment {
    private String assignment;
    private int maxScore;
    private double earnedScore;
    private String description;
    private String dateDue;
    // Primary Key auto-gen
    @PrimaryKey (autoGenerate = true)
    private int assignmentId;

    public Assignment(String assignment, int maxScore, double earnedScore, String description, String dateDue) {
        this.assignment = assignment;
        this.maxScore = maxScore;
        this.earnedScore = earnedScore;
        this.description = description;
        this.dateDue = dateDue;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public double getEarnedScore() {
        return earnedScore;
    }

    public void setEarnedScore(double earnedScore) {
        this.earnedScore = earnedScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }
}
