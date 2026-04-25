package com.examportal.dao;

import com.examportal.database.DBConnection;
import com.examportal.models.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    private Connection connection = DBConnection.getConnection();

    // Save result to database
    public boolean saveResult(Result result) {

        String query = "INSERT INTO results (student_id, exam_id, score, total, percentage, grade) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, result.getStudentId());
            statement.setInt(2, result.getExamId());
            statement.setInt(3, result.getScore());
            statement.setInt(4, result.getTotal());
            statement.setDouble(5, result.getPercentage());
            statement.setString(6, result.getGrade());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error saving result!");
            e.printStackTrace();
        }

        return false;
    }

    // Get results by student id
    public List<Result> getResultsByStudentId(int studentId) {

        List<Result> results = new ArrayList<>();
        String query = "SELECT * FROM results WHERE student_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Result result = new Result(
                    resultSet.getInt("id"),
                    resultSet.getInt("student_id"),
                    resultSet.getInt("exam_id"),
                    resultSet.getInt("score"),
                    resultSet.getInt("total"),
                    resultSet.getDouble("percentage"),
                    resultSet.getString("grade"),
                    resultSet.getString("submitted_at")
                );
                results.add(result);
            }

        } catch (SQLException e) {
            System.out.println("Error getting results!");
            e.printStackTrace();
        }

        return results;
    }

    // Get all results for admin
    public void displayAllResults() {

        String query = "SELECT r.*, u.name FROM results r JOIN users u ON r.student_id = u.id";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("==========================================");
            System.out.println("ALL STUDENT RESULTS");
            System.out.println("==========================================");

            boolean found = false;
            while (resultSet.next()) {
                found = true;
                System.out.println("Student    : " + resultSet.getString("name"));
                System.out.println("Exam ID    : " + resultSet.getInt("exam_id"));
                System.out.println("Score      : " + resultSet.getInt("score") + "/" + resultSet.getInt("total"));
                System.out.println("Percentage : " + resultSet.getDouble("percentage") + "%");
                System.out.println("Grade      : " + resultSet.getString("grade"));
                System.out.println("Submitted  : " + resultSet.getString("submitted_at"));
                System.out.println("------------------------------------------");
            }

            if (!found) {
                System.out.println("No results found.");
            }

        } catch (SQLException e) {
            System.out.println("Error displaying results!");
            e.printStackTrace();
        }
    }
}