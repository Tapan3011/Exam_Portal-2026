package com.examportal.controllers;

import com.examportal.database.DBConnection;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    // ── REGISTER ──
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> body) {

        Map<String, Object> response = new HashMap<>();

        String name     = body.get("name");
        String email    = body.get("email");
        String password = body.get("password");
        String role     = body.get("role");

        // Validate inputs
        if (name == null || email == null || password == null || role == null) {
            response.put("success", false);
            response.put("message", "All fields are required.");
            return response;
        }

        // Check if email already exists
        try {
            Connection connection = DBConnection.getConnection();
            String checkQuery = "SELECT id FROM users WHERE email = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, email);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        response.put("success", false);
                        response.put("message", "Email already registered. Please login.");
                        return response;
                    }
                }
            }

        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Database error while checking email.");
            return response;
        }

        // Insert new user
        try {
            Connection connection = DBConnection.getConnection();
            String insertQuery = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, name);
                insertStmt.setString(2, email);
                insertStmt.setString(3, password);
                insertStmt.setString(4, role);
                insertStmt.executeUpdate();
            }

            response.put("success", true);
            response.put("message", "Registration successful! You can now login.");

        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Registration failed. Please try again.");
            e.printStackTrace();
        }

        return response;
    }

    // ── LOGIN ──
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {

        Map<String, Object> response = new HashMap<>();

        String email    = body.get("email");
        String password = body.get("password");

        if (email == null || password == null) {
            response.put("success", false);
            response.put("message", "Email and password are required.");
            return response;
        }

        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        response.put("success", true);
                        response.put("message", "Login successful!");
                        response.put("id",      rs.getInt("id"));
                        response.put("name",    rs.getString("name"));
                        response.put("email",   rs.getString("email"));
                        response.put("role",    rs.getString("role"));
                    } else {
                        response.put("success", false);
                        response.put("message", "Invalid email or password.");
                    }
                }
            }

        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Database error during login.");
            e.printStackTrace();
        }

        return response;
    }
}