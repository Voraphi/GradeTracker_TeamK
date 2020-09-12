package com.example.gradetracker_teamk.RecyclerView;

public class AssignmentItems {
    String assignment;
    String dateDue;
    String earnedScore;
    String maxScore;

    public AssignmentItems(String assignment, String dateDue, String earnedScore, String maxScore) {
        this.assignment = assignment;
        this.dateDue = dateDue;
        this.earnedScore = earnedScore;
        this.maxScore = maxScore;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getEarnedScore() {
        return earnedScore;
    }

    public void setEarnedScore(String earnedScore) {
        this.earnedScore = earnedScore;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }
}
