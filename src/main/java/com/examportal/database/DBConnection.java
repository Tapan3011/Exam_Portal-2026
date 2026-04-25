package com.examportal.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database credentials
    private static final String URL      = "jdbc:mysql://localhost:3306/exam_portal";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Tapan@#3011";

    // Single connection object
    private static Connection connection = null;

    // Private constructor so no one can create object of this class
    private DBConnection() {
    }

    // Method to get connection
    public static Connection getConnection() {

        try {
            if (connection == null || connection.isClosed()) {
                // Load MySQL driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create connection
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                System.out.println("Database connected successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed or checking status failed!");
            e.printStackTrace();
        }

        return connection;
    }

    // Method to close connection
    public static void closeConnection() {

        if (connection != null) {

            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");

            } catch (SQLException e) {
                System.out.println("Error closing connection!");
                e.printStackTrace();
            }
        }
    }
}