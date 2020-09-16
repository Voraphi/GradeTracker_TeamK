package com.example.gradetracker_teamk.RecyclerView;

public class AssignmentItems {
    String assignment;
    String dateDue;
    double earnedScore;
    int maxScore;

    public AssignmentItems(String assignment, String dateDue, double earnedScore, int maxScore) {
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

    public double getEarnedScore() {
        return earnedScore;
    }

    public void setEarnedScore(double earnedScore) {
        this.earnedScore = earnedScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
}
