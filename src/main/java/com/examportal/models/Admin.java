package com.examportal.models;

public class Admin extends User {

    // Constructor
    public Admin(int id, String name, String email, String password) {
        // Calling parent class constructor - Inheritance
        super(id, name, email, password, "ADMIN");
    }

    // Implementing abstract method - Polymorphism
    @Override
    public void showDashboard() {
        System.out.println("==========================================");
        System.out.println("         ADMIN DASHBOARD                  ");
        System.out.println("==========================================");
        System.out.println("1. Create Exam");
        System.out.println("2. View All Questions");
        System.out.println("3. View All Results");
        System.out.println("4. View My Profile");
        System.out.println("5. Logout");
        System.out.println("==========================================");
    }
}