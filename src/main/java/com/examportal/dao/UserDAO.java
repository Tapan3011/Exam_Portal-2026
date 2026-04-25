package com.examportal.dao;

import com.examportal.database.DBConnection;
import com.examportal.models.Admin;
import com.examportal.models.Student;
import com.examportal.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Get connection from DBConnection class
    private Connection connection = DBConnection.getConnection();

    // Method to find user by email and password for login
    public User getUserByEmailAndPassword(String email, String password) {

        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                int    id   = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");

                // Return Admin or Student object based on role
                // This is Polymorphism
                if (role.equals("ADMIN")) {
                    return new Admin(id, name, email, password);
                } else {
                    return new Student(id, name, email, password);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding user!");
            e.printStackTrace();
        }

        return null;
    }

    // Method to get all students
    public void getAllStudents() {

        String query = "SELECT * FROM users WHERE role = 'STUDENT'";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("==========================================");
            System.out.println("ALL STUDENTS");
            System.out.println("==========================================");

            while (resultSet.next()) {
                System.out.println("ID    : " + resultSet.getInt("id"));
                System.out.println("Name  : " + resultSet.getString("name"));
                System.out.println("Email : " + resultSet.getString("email"));
                System.out.println("------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error getting students!");
            e.printStackTrace();
        }
    }
}