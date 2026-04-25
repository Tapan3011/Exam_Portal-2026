package com.examportal.models;

public class Student extends User {

    // Constructor
    public Student(int id, String name, String email, String password) {
        // Calling parent class constructor - Inheritance
        super(id, name, email, password, "STUDENT");
    }

    // Implementing abstract method - Polymorphism
    // Same method name but different behaviour than Admin
    @Override
    public void showDashboard() {
        System.out.println("==========================================");
        System.out.println("         STUDENT DASHBOARD                ");
        System.out.println("==========================================");
        System.out.println("1. View Available Exams");
        System.out.println("2. Attempt Exam");
        System.out.println("3. View My Results");
        System.out.println("4. View My Profile");
        System.out.println("5. Logout");
        System.out.println("==========================================");
    }
}