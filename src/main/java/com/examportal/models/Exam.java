package com.examportal.models;

public class Exam {

    // Private fields - Encapsulation
    private int    id;
    private String title;
    private String subject;
    private int    duration;
    private int    questionCount;
    private int    createdBy;

    // Constructor
    public Exam(int id, String title, String subject, int duration, int questionCount, int createdBy) {
        this.id            = id;
        this.title         = title;
        this.subject       = subject;
        this.duration      = duration;
        this.questionCount = questionCount;
        this.createdBy     = createdBy;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getSubject() { return subject; }
    public int getDuration() { return duration; }
    public int getQuestionCount() { return questionCount; }
    public int getCreatedBy() { return createdBy; }

    // Display exam info
    public void displayExam() {
        System.out.println("------------------------------------------");
        System.out.println("ID      : " + id);
        System.out.println("Title   : " + title);
        System.out.println("Subject : " + subject);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Questions: " + questionCount);
        System.out.println("------------------------------------------");
    }
}