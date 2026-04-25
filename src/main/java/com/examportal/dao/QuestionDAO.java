package com.examportal.dao;

import com.examportal.database.DBConnection;
import com.examportal.models.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionDAO {

    private Connection connection = DBConnection.getConnection();

    // Get all questions from database
    public List<Question> getAllQuestions() {

        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Question q = new Question(
                    resultSet.getInt("id"),
                    resultSet.getString("subject"),
                    resultSet.getString("question_text"),
                    resultSet.getString("option_a"),
                    resultSet.getString("option_b"),
                    resultSet.getString("option_c"),
                    resultSet.getString("option_d"),
                    resultSet.getString("correct_option").charAt(0)
                );
                questions.add(q);
            }

        } catch (SQLException e) {
            System.out.println("Error getting questions!");
            e.printStackTrace();
        }

        return questions;
    }

    // Get random questions for exam
    public List<Question> getRandomQuestions(int count) {

        List<Question> allQuestions = getAllQuestions();

        // Shuffle the list randomly
        Collections.shuffle(allQuestions);

        // Return only required number of questions
        if (count > allQuestions.size()) {
            count = allQuestions.size();
        }

        return allQuestions.subList(0, count);
    }

    // Display all questions for admin
    public void displayAllQuestions() {

        List<Question> questions = getAllQuestions();

        System.out.println("==========================================");
        System.out.println("ALL QUESTIONS IN QUESTION BANK");
        System.out.println("==========================================");

        int number = 1;
        for (Question q : questions) {
            System.out.println("Q" + number + ". [" + q.getSubject() + "] " + q.getQuestionText());
            System.out.println("   A. " + q.getOptionA());
            System.out.println("   B. " + q.getOptionB());
            System.out.println("   C. " + q.getOptionC());
            System.out.println("   D. " + q.getOptionD());
            System.out.println("   Correct: " + q.getCorrectOption());
            System.out.println("------------------------------------------");
            number++;
        }
    }
}