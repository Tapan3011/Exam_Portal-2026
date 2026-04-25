package com.examportal.services;

import com.examportal.dao.ExamDAO;
import com.examportal.dao.QuestionDAO;
import com.examportal.models.Exam;
import com.examportal.models.Question;
import com.examportal.models.Result;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
public class ExamService {

    private ExamDAO      examDAO      = new ExamDAO();
    private QuestionDAO  questionDAO  = new QuestionDAO();
    private ResultService resultService = new ResultService();

    // Timer variables
    private boolean timeUp = false;
    private int     timeLeft;

    // Admin creates exam
    public void createExam(int adminId, Scanner scanner) {

        System.out.println("\n==========================================");
        System.out.println("CREATE NEW EXAM");
        System.out.println("==========================================");

        System.out.print("Enter Exam Title   : ");
        String title = scanner.nextLine();

        System.out.print("Enter Subject      : ");
        String subject = scanner.nextLine();

        System.out.print("Enter Duration (minutes): ");
        int duration = Integer.parseInt(scanner.nextLine());

        System.out.print("Number of Questions: ");
        int questionCount = Integer.parseInt(scanner.nextLine());

        Exam exam = new Exam(0, title, subject, duration, questionCount, adminId);

        boolean saved = examDAO.saveExam(exam);

        if (saved) {
            System.out.println("Exam created successfully!");
        } else {
            System.out.println("Failed to create exam.");
        }
    }

    // Student attempts exam
    public void attemptExam(int studentId, int examId, Scanner scanner) {

        Exam exam = examDAO.getExamById(examId);

        if (exam == null) {
            System.out.println("Exam not found!");
            return;
        }

        System.out.println("\n==========================================");
        System.out.println("STARTING EXAM: " + exam.getTitle());
        System.out.println("Duration: " + exam.getDuration() + " minutes");
        System.out.println("Questions: " + exam.getQuestionCount());
        System.out.println("==========================================");
        System.out.println("Press Enter to start...");
        scanner.nextLine();

        // Get random questions
        List<Question> questions = questionDAO.getRandomQuestions(exam.getQuestionCount());

        // Start timer in background thread
        timeUp   = false;
        timeLeft = exam.getDuration() * 60;
        startTimer();

        int score   = 0;
        int total   = questions.size();
        char[] userAnswers = new char[total];

        // Ask each question
        for (int i = 0; i < questions.size(); i++) {

            // Check for time is up
            if (timeUp) {
                System.out.println("\n\nTIME IS UP! Auto submitting...");
                break;
            }

            Question question = questions.get(i);
            System.out.println("\nTime Remaining: " + (timeLeft / 60) + " min " + (timeLeft % 60) + " sec");
            question.displayQuestion(i + 1);

            String input = scanner.nextLine().toUpperCase().trim();

            if (input.isEmpty()) {
                userAnswers[i] = ' ';
            } else {
                userAnswers[i] = input.charAt(0);
            }

        
            if (userAnswers[i] == question.getCorrectOption()) {
                score++;
            }
        }

      
        timeUp = true;

        // Save and display result
        Result result = resultService.saveResult(studentId, examId, score, total);
        result.displayResult();
    }

    // Start countdown timer in background
    private void startTimer() {

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeLeft <= 0) {
                    timeUp = true;
                    timer.cancel();
                } else {
                    timeLeft--;
                }
            }
        }, 1000, 1000);
    }
}