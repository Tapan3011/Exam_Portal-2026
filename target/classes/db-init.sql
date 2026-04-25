-- Create database if not exists
CREATE DATABASE IF NOT EXISTS exam_portal;
USE exam_portal;

-- Create users table if not exists
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Insert demo users (these will be ignored if they already exist)
INSERT IGNORE INTO users (id, name, email, password, role) VALUES
(1, 'Admin', 'admin@exam.com', 'admin123', 'ADMIN'),
(2, 'Priya Sharma', 'priya@exam.com', 'priya123', 'STUDENT'),
(3, 'Arjun Mehta', 'arjun@exam.com', 'arjun123', 'STUDENT');

-- Create exams table if not exists
CREATE TABLE IF NOT EXISTS exams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    duration_minutes INT NOT NULL,
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

-- Create questions table if not exists
CREATE TABLE IF NOT EXISTS questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    exam_id INT NOT NULL,
    question_text TEXT NOT NULL,
    option_a VARCHAR(200) NOT NULL,
    option_b VARCHAR(200) NOT NULL,
    option_c VARCHAR(200) NOT NULL,
    option_d VARCHAR(200) NOT NULL,
    correct_answer CHAR(1) NOT NULL,
    marks INT DEFAULT 1,
    FOREIGN KEY (exam_id) REFERENCES exams(id)
);

-- Create results table if not exists
CREATE TABLE IF NOT EXISTS results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    exam_id INT NOT NULL,
    score INT NOT NULL,
    total_marks INT NOT NULL,
    percentage DECIMAL(5,2) NOT NULL,
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (exam_id) REFERENCES exams(id)
);

-- Insert sample exam
INSERT IGNORE INTO exams (id, title, description, duration_minutes, created_by) VALUES
(1, 'Java Programming Basics', 'Test your knowledge of Java fundamentals including OOP concepts, data types, and control structures.', 30, 1);

-- Insert sample questions for Java exam
INSERT IGNORE INTO questions (exam_id, question_text, option_a, option_b, option_c, option_d, correct_answer, marks) VALUES
(1, 'What is the correct way to declare a main method in Java?', 'public static void main(String[] args)', 'public void main(String[] args)', 'static void main(String[] args)', 'private static void main(String[] args)', 'A', 1),
(1, 'Which keyword is used to inherit a class in Java?', 'implements', 'extends', 'inherits', 'super', 'B', 1),
(1, 'What is the size of int variable in Java?', '8 bits', '16 bits', '32 bits', '64 bits', 'C', 1),
(1, 'Which of these is not a Java feature?', 'Dynamic', 'Architecture Neutral', 'Use of pointers', 'Object-oriented', 'C', 1),
(1, 'What is the default value of boolean variable in Java?', 'true', 'false', 'null', '0', 'B', 1);
