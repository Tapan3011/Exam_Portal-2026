package com.examportal.dao;

import com.examportal.database.DBConnection;
import com.examportal.models.Exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {

    private Connection connection = DBConnection.getConnection();

    public boolean saveExam(Exam exam) {

        String query = "INSERT INTO exams (title, subject, duration, question_count, created_by) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, exam.getTitle());
            statement.setString(2, exam.getSubject());
            statement.setInt(3, exam.getDuration());
            statement.setInt(4, exam.getQuestionCount());
            statement.setInt(5, exam.getCreatedBy());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error saving exam!");
            e.printStackTrace();
        }

        return false;
    }

    public List<Exam> getAllExams() {

        List<Exam> exams = new ArrayList<>();
        String query = "SELECT * FROM exams";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Exam exam = new Exam(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("subject"),
                    resultSet.getInt("duration"),
                    resultSet.getInt("question_count"),
                    resultSet.getInt("created_by")
                );
                exams.add(exam);
            }

        } catch (SQLException e) {
            System.out.println("Error getting exams!");
            e.printStackTrace();
        }

        return exams;
    }

    public Exam getExamById(int id) {

        String query = "SELECT * FROM exams WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Exam(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("subject"),
                    resultSet.getInt("duration"),
                    resultSet.getInt("question_count"),
                    resultSet.getInt("created_by")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error getting exam!");
            e.printStackTrace();
        }

        return null;
    }

    public void displayAllExams() {

        List<Exam> exams = getAllExams();

        System.out.println("==========================================");
        System.out.println("AVAILABLE EXAMS");
        System.out.println("==========================================");

        if (exams.isEmpty()) {
            System.out.println("No exams available.");
            return;
        }

        for (Exam exam : exams) {
            exam.displayExam();
        }
    }
}