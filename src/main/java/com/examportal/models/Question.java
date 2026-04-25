package com.examportal.models;

public class Question {

    // Private fields - Encapsulation
    private int    id;
    private String subject;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private char   correctOption;

    // Constructor
    public Question(int id, String subject, String questionText,
                    String optionA, String optionB,
                    String optionC, String optionD, char correctOption) {
        this.id            = id;
        this.subject       = subject;
        this.questionText  = questionText;
        this.optionA       = optionA;
        this.optionB       = optionB;
        this.optionC       = optionC;
        this.optionD       = optionD;
        this.correctOption = correctOption;
    }

    // Getters
    public int getId() { return id; }
    public String getSubject() { return subject; }
    public String getQuestionText() { return questionText; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public char getCorrectOption() { return correctOption; }

    // Display question nicely
    public void displayQuestion(int number) {
        System.out.println("\nQ" + number + ". " + questionText);
        System.out.println("   A. " + optionA);
        System.out.println("   B. " + optionB);
        System.out.println("   C. " + optionC);
        System.out.println("   D. " + optionD);
        System.out.print("Your Answer (A/B/C/D): ");
    }
}