package com.examportal.controllers;

import com.examportal.database.DBConnection;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ExamController {

    // ── GET ALL EXAMS ──
    @GetMapping("/exams")
    public List<Map<String, Object>> getAllExams() {

        List<Map<String, Object>> exams = new ArrayList<>();

        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM exams";
            try (PreparedStatement stmt = connection.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> exam = new HashMap<>();
                    exam.put("id",            rs.getInt("id"));
                    exam.put("title",         rs.getString("title"));
                    exam.put("subject",       rs.getString("subject"));
                    exam.put("duration",      rs.getInt("duration"));
                    exam.put("questionCount", rs.getInt("question_count"));
                    exam.put("createdBy",     rs.getInt("created_by"));
                    exams.add(exam);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exams;
    }

    // ── CREATE EXAM ──
    @PostMapping("/exams")
    public Map<String, Object> createExam(@RequestBody Map<String, Object> body) {

        Map<String, Object> response = new HashMap<>();

        try {
            Connection connection = DBConnection.getConnection();
            String query = "INSERT INTO exams (title, subject, duration, question_count, created_by) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, (String) body.get("title"));
                stmt.setString(2, (String) body.get("subject"));
                stmt.setInt(3,    (Integer) body.get("duration"));
                stmt.setInt(4,    (Integer) body.get("questionCount"));
                stmt.setInt(5,    (Integer) body.get("createdBy"));
                stmt.executeUpdate();
            }

            response.put("success", true);
            response.put("message", "Exam created successfully!");

        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Failed to create exam.");
            e.printStackTrace();
        }

        return response;
    }

    // ── DELETE EXAM ──
    @DeleteMapping("/exams/{id}")
    public Map<String, Object> deleteExam(@PathVariable int id) {

        Map<String, Object> response = new HashMap<>();

        try {
            Connection connection = DBConnection.getConnection();
            String query = "DELETE FROM exams WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            response.put("success", true);
            response.put("message", "Exam deleted successfully!");

        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Failed to delete exam.");
            e.printStackTrace();
        }

        return response;
    }

    // ── GET ALL QUESTIONS ──
    @GetMapping("/questions")
    public List<Map<String, Object>> getAllQuestions() {

        List<Map<String, Object>> questions = new ArrayList<>();

        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM questions";
            try (PreparedStatement stmt = connection.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> q = new HashMap<>();
                    q.put("id",            rs.getInt("id"));
                    q.put("subject",       rs.getString("subject"));
                    q.put("questionText",  rs.getString("question_text"));
                    q.put("optionA",       rs.getString("option_a"));
                    q.put("optionB",       rs.getString("option_b"));
                    q.put("optionC",       rs.getString("option_c"));
                    q.put("optionD",       rs.getString("option_d"));
                    q.put("correctOption", rs.getString("correct_option"));
                    questions.add(q);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    // ── CREATE QUESTION ──
    @PostMapping("/questions")
    public Map<String, Object> createQuestion(@RequestBody Map<String, Object> body) {

        Map<String, Object> response = new HashMap<>();

        try {
            Connection connection = DBConnection.getConnection();
            String query = "INSERT INTO questions (subject, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, (String) body.get("subject"));
                stmt.setString(2, (String) body.get("questionText"));
                stmt.setString(3, (String) body.get("optionA"));
                stmt.setString(4, (String) body.get("optionB"));
                stmt.setString(5, (String) body.get("optionC"));
                stmt.setString(6, (String) body.get("optionD"));
                stmt.setString(7, (String) body.get("correctOption"));
                stmt.executeUpdate();
            }

            response.put("success", true);
            response.put("message", "Question created successfully!");

        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Failed to create question: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    // ── SAVE RESULT ──
    @PostMapping("/results")
    public Map<String, Object> saveResult(@RequestBody Map<String, Object> body) {

        Map<String, Object> response = new HashMap<>();

        try {
            Connection connection = DBConnection.getConnection();
            String query = "INSERT INTO results (student_id, exam_id, score, total, percentage, grade) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1,    (Integer) body.get("studentId"));
                stmt.setInt(2,    (Integer) body.get("examId"));
                stmt.setInt(3,    (Integer) body.get("score"));
                stmt.setInt(4,    (Integer) body.get("total"));
                stmt.setDouble(5, ((Number) body.get("percentage")).doubleValue());
                stmt.setString(6, (String) body.get("grade"));
                stmt.executeUpdate();
            }

            response.put("success", true);
            response.put("message", "Result saved successfully!");

        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Failed to save result.");
            e.printStackTrace();
        }

        return response;
    }

    // ── GET RESULTS BY STUDENT ──
    @GetMapping("/results/{studentId}")
    public List<Map<String, Object>> getResultsByStudent(@PathVariable int studentId) {

        List<Map<String, Object>> results = new ArrayList<>();

        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT r.*, e.title FROM results r JOIN exams e ON r.exam_id = e.id WHERE r.student_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, studentId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> result = new HashMap<>();
                        result.put("id",         rs.getInt("id"));
                        result.put("examTitle",  rs.getString("title"));
                        result.put("score",      rs.getInt("score"));
                        result.put("total",      rs.getInt("total"));
                        result.put("percentage", rs.getDouble("percentage"));
                        result.put("grade",      rs.getString("grade"));
                        result.put("date",       rs.getString("submitted_at"));
                        results.add(result);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    // ── GET ALL RESULTS (Admin) ──
    @GetMapping("/results")
    public List<Map<String, Object>> getAllResults() {

        List<Map<String, Object>> results = new ArrayList<>();

        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT r.*, u.name as student_name, e.title as exam_title FROM results r JOIN users u ON r.student_id = u.id JOIN exams e ON r.exam_id = e.id";
            try (PreparedStatement stmt = connection.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("id",          rs.getInt("id"));
                    result.put("studentName", rs.getString("student_name"));
                    result.put("examTitle",   rs.getString("exam_title"));
                    result.put("score",       rs.getInt("score"));
                    result.put("total",       rs.getInt("total"));
                    result.put("percentage",  rs.getDouble("percentage"));
                    result.put("grade",       rs.getString("grade"));
                    result.put("date",        rs.getString("submitted_at"));
                    results.add(result);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    // ── GET ALL STUDENTS (Admin) ──
    @GetMapping("/students")
    public List<Map<String, Object>> getAllStudents() {

        List<Map<String, Object>> students = new ArrayList<>();

        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT id, name, email FROM users WHERE role = 'STUDENT'";
            try (PreparedStatement stmt = connection.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> student = new HashMap<>();
                    student.put("id",    rs.getInt("id"));
                    student.put("name",  rs.getString("name"));
                    student.put("email", rs.getString("email"));
                    students.add(student);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}