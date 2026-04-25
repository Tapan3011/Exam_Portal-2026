package com.examportal.models;

public class Result {

    // Private fields - Encapsulation
    private int    id;
    private int    studentId;
    private int    examId;
    private int    score;
    private int    total;
    private double percentage;
    private String grade;
    private String submittedAt;

    // Constructor
    public Result(int id, int studentId, int examId,
                  int score, int total,
                  double percentage, String grade, String submittedAt) {
        this.id          = id;
        this.studentId   = studentId;
        this.examId      = examId;
        this.score       = score;
        this.total       = total;
        this.percentage  = percentage;
        this.grade       = grade;
        this.submittedAt = submittedAt;
    }

    // Getters
    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getExamId() { return examId; }
    public int getScore() { return score; }
    public int getTotal() { return total; }
    public double getPercentage() { return percentage; }
    public String getGrade() { return grade; }
    public String getSubmittedAt() { return submittedAt; }

    // Display result nicely
    public void displayResult() {
        System.out.println("==========================================");
        System.out.println("             EXAM RESULT                  ");
        System.out.println("==========================================");
        System.out.println("Score      : " + score + " / " + total);
        System.out.println("Percentage : " + percentage + "%");
        System.out.println("Grade      : " + grade);
        System.out.println("Submitted  : " + submittedAt);
        System.out.println("==========================================");
    }
}