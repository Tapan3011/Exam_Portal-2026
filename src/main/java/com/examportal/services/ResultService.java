package com.examportal.services;

import com.examportal.dao.ResultDAO;
import com.examportal.interfaces.Reportable;
import com.examportal.models.Result;

import java.util.List;

public class ResultService implements Reportable {

    private ResultDAO resultDAO = new ResultDAO();

    // Calculate grade based on percentage
    public String calculateGrade(double percentage) {
        if (percentage >= 80) return "Excellent";
        else if (percentage >= 60) return "Good";
        else if (percentage >= 40) return "Average";
        else return "Needs Improvement";
    }

    // Calculate score and save result
    public Result saveResult(int studentId, int examId, int score, int total) {

        double percentage = Math.round((score * 100.0) / total * 100.0) / 100.0;
        String grade      = calculateGrade(percentage);

        Result result = new Result(0, studentId, examId, score, total, percentage, grade, "");

        boolean saved = resultDAO.saveResult(result);

        if (saved) {
            System.out.println("Result saved successfully!");
        }

        return result;
    }

    // Get results for a student
    public void showStudentResults(int studentId) {

        List<Result> results = resultDAO.getResultsByStudentId(studentId);

        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        System.out.println("==========================================");
        System.out.println("YOUR RESULTS");
        System.out.println("==========================================");

        for (Result result : results) {
            result.displayResult();
        }
    }

    // Reportable interface method implementation
    // This is Polymorphism - Reportable interface
    @Override
    public void generateReport(int id) {
        resultDAO.displayAllResults();
    }
}