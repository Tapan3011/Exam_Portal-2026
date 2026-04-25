package com.examportal;

import com.examportal.database.DBConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.Statement;

@SpringBootApplication
public class ExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
        
        // Initialize database with demo data
        initializeDatabase();
        
        System.out.println("==========================================");
        System.out.println("   ExamPortal Server Started!             ");
        System.out.println("   Running at: http://localhost:8080      ");
        System.out.println("==========================================");
    }
    
    private static void initializeDatabase() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            
            // Create database if not exists
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS exam_portal");
            stmt.executeUpdate("USE exam_portal");
            
            // Create users table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) UNIQUE NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
                "role VARCHAR(20) NOT NULL" +
                ")");
            
            // Create study_materials table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS study_materials (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "original_name VARCHAR(255) NOT NULL," +
                "file_name VARCHAR(255) NOT NULL UNIQUE," +
                "file_size BIGINT NOT NULL," +
                "upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "created_by INT," +
                "FOREIGN KEY (created_by) REFERENCES users(id)" +
                ")");
            
            // Create indexes for study_materials table
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_study_materials_upload_date ON study_materials(upload_date)");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_study_materials_created_by ON study_materials(created_by)");
            
            // Insert demo users (ignore if they exist)
            stmt.executeUpdate("INSERT IGNORE INTO users (id, name, email, password, role) VALUES " +
                "(1, 'Admin', 'admin@exam.com', 'admin123', 'ADMIN')," +
                "(2, 'Priya Sharma', 'priya@exam.com', 'priya123', 'STUDENT')," +
                "(3, 'Arjun Mehta', 'arjun@exam.com', 'arjun123', 'STUDENT')");
            
            System.out.println("Database initialized with demo users!");
            System.out.println("Demo Login Credentials:");
            System.out.println("Admin: admin@exam.com / admin123");
            System.out.println("Student: priya@exam.com / priya123");
            System.out.println("Student: arjun@exam.com / arjun123");
            
        } catch (Exception e) {
            System.out.println("Database initialization failed: " + e.getMessage());
        }
    }
}