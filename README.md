# 📝 Online Examination System

**A fully functional web-based Online Examination System built with Java Spring Boot and MySQL.**  
*Featuring timed exams, auto-evaluation, instant results, and a beautiful Purple & White UI.*

[🚀 Features](#-features) • [🏗️ Architecture](#️-architecture) • [📦 Installation](#-installation) • [🎓 OOP Concepts](#-oop-concepts-used) • [📸 Screenshots](#-project-structure) • [🤝 Contributing](#-contributing)

</div>

---

## 📌 Table of Contents

- [About the Project](#-about-the-project)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#️-architecture)
- [Project Structure](#-project-structure)
- [OOP Concepts Used](#-oop-concepts-used)
- [Database Schema](#️-database-schema)
- [Installation](#-installation)
- [How to Run](#-how-to-run)
- [API Endpoints](#-api-endpoints)
- [Default Credentials](#-default-credentials)
- [Contributing](#-contributing)

---

## 🎯 About the Project

The **Online Examination System** is a full-stack web application developed as part of a college project to demonstrate **Object-Oriented Programming (OOP)** concepts using Java. It provides a complete examination platform where:

- 👨‍💼 **Admins** can create and manage exams, view student results, and manage the question bank
- 🎓 **Students** can register, attempt timed exams, and receive instant results with detailed answer reviews
- 🗄️ **MySQL** stores all user data, exam records, and results persistently
- 🌐 **Spring Boot** serves the frontend and exposes REST APIs for full backend connectivity

> This project clearly demonstrates **Abstraction**, **Inheritance**, **Encapsulation**, and **Polymorphism** through a real-world application.

---

## ✨ Features

### 👨‍💼 Admin Features
| Feature | Description |
|---------|-------------|
| 📋 Create Exams | Create exams with title, subject, duration, and question count |
| ❓ Question Bank | View all 24 questions across 6 subjects |
| 🏆 View Results | See all student results with scores and grades |
| 👥 Manage Students | View all registered students and their performance |
| 🗑️ Delete Exams | Remove exams from the system |

### 🎓 Student Features
| Feature | Description |
|---------|-------------|
| 📝 Register & Login | Create a personal account saved to MySQL database |
| 📋 Browse Exams | View all available exams with details |
| ⏱️ Timed Exam | Live countdown timer with auto-submission |
| 🎲 Random Questions | Questions randomly selected each attempt |
| ⚡ Instant Results | Score, percentage, and grade shown immediately |
| 🔍 Answer Review | Full question-by-question review after submission |
| 📊 Result History | View all past exam attempts and scores |

### 🌟 System Features
```
✅ Role-based authentication (Admin / Student)
✅ Live countdown timer with warning colors
✅ Auto-submit when time expires
✅ Random question selection per attempt
✅ Instant score calculation
✅ Dark mode toggle
✅ Fully responsive design
✅ REST API backend with Spring Boot
✅ MySQL persistent storage
✅ Beautiful Purple & White UI
```

---

## 🛠️ Tech Stack

```
┌─────────────────────────────────────────────────────────┐
│                    TECH STACK                           │
├─────────────────┬───────────────────────────────────────┤
│   FRONTEND      │  HTML5, CSS3, JavaScript (Vanilla)    │
│   BACKEND       │  Java 21, Spring Boot 3.2.0           │
│   DATABASE      │  MySQL 8.0                            │
│   BUILD TOOL    │  Apache Maven 3.9                     │
│   CONNECTIVITY  │  JDBC (MySQL Connector/J 8.0.33)      │
│   SERVER        │  Spring Boot Embedded Tomcat          │
│   IDE           │  VS Code                              │
│   VERSION CTRL  │  Git & GitHub                         │
└─────────────────┴───────────────────────────────────────┘
```

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    SYSTEM ARCHITECTURE                      │
└─────────────────────────────────────────────────────────────┘

  BROWSER (HTML/CSS/JS)
       │
       │  HTTP Requests (fetch API)
       ▼
  ┌─────────────────────────────────┐
  │     SPRING BOOT SERVER          │
  │     (localhost:8080)            │
  │                                 │
  │  ┌───────────────────────────┐  │
  │  │      CONTROLLERS          │  │
  │  │  AuthController.java      │  │
  │  │  ExamController.java      │  │
  │  └────────────┬──────────────┘  │
  │               │                 │
  │  ┌────────────▼──────────────┐  │
  │  │       SERVICES            │  │
  │  │  AuthService.java         │  │
  │  │  ExamService.java         │  │
  │  │  ResultService.java       │  │
  │  └────────────┬──────────────┘  │
  │               │                 │
  │  ┌────────────▼──────────────┐  │
  │  │         DAO LAYER         │  │
  │  │  UserDAO.java             │  │
  │  │  ExamDAO.java             │  │
  │  │  QuestionDAO.java         │  │
  │  │  ResultDAO.java           │  │
  │  └────────────┬──────────────┘  │
  └───────────────┼─────────────────┘
                  │  JDBC
                  ▼
  ┌─────────────────────────────────┐
  │         MySQL DATABASE          │
  │         (exam_portal)           │
  │                                 │
  │  users | exams | questions      │
  │  results | exam_questions       │
  └─────────────────────────────────┘
```

---

## 📁 Project Structure

```
OnlineExamSystem/
│
├── 📄 pom.xml                          ← Maven dependencies
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/examportal/
│       │       │
│       │       ├── 🚀 ExamApplication.java      ← Spring Boot entry point
│       │       │
│       │       ├── models/                       ← OOP Model Classes
│       │       │   ├── 👤 User.java              ← Abstract base class
│       │       │   ├── 👨‍💼 Admin.java             ← Extends User
│       │       │   ├── 🎓 Student.java            ← Extends User
│       │       │   ├── ❓ Question.java           ← Question model
│       │       │   ├── 📋 Exam.java              ← Exam model
│       │       │   └── 📊 Result.java            ← Result model
│       │       │
│       │       ├── interfaces/                   ← OOP Interfaces
│       │       │   ├── 🔐 Authenticable.java     ← Login/Logout contract
│       │       │   └── 📈 Reportable.java        ← Report contract
│       │       │
│       │       ├── dao/                          ← Database Access Layer
│       │       │   ├── UserDAO.java              ← User DB operations
│       │       │   ├── ExamDAO.java              ← Exam DB operations
│       │       │   ├── QuestionDAO.java          ← Question DB operations
│       │       │   └── ResultDAO.java            ← Result DB operations
│       │       │
│       │       ├── services/                     ← Business Logic Layer
│       │       │   ├── AuthService.java          ← Authentication logic
│       │       │   ├── ExamService.java          ← Exam + timer logic
│       │       │   └── ResultService.java        ← Score calculation
│       │       │
│       │       ├── controllers/                  ← REST API Controllers
│       │       │   ├── AuthController.java       ← /api/auth endpoints
│       │       │   └── ExamController.java       ← /api/exams endpoints
│       │       │
│       │       ├── config/
│       │       │   └── CorsConfig.java           ← CORS configuration
│       │       │
│       │       └── database/
│       │           └── DBConnection.java         ← MySQL JDBC connection
│       │
│       └── resources/
│           ├── static/                           ← Frontend Files
│           │   ├── css/
│           │   │   └── style.css                ← Purple theme styles
│           │   ├── index.html                   ← Landing page
│           │   ├── login.html                   ← Login page
│           │   ├── register.html                ← Registration page
│           │   ├── student.html                 ← Student dashboard
│           │   ├── admin.html                   ← Admin dashboard
│           │   ├── exam.html                    ← Exam engine
│           │   └── result.html                  ← Result page
│           │
│           └── application.properties           ← DB configuration
```

---

## 🎓 OOP Concepts Used

This project is specifically designed to demonstrate all four pillars of **Object-Oriented Programming**:

### 1️⃣ Abstraction
```java
// User.java — Abstract class hides implementation details
public abstract class User {
    private int id;
    private String name;
    private String email;

    // Abstract method — every subclass MUST implement this
    public abstract void showDashboard();

    // Common method available to all users
    public void showProfile() {
        System.out.println("Name: " + name);
    }
}
```
> 📌 `User` is abstract — you cannot create a `User` object directly. Only `Admin` and `Student` can be instantiated.

---

### 2️⃣ Inheritance
```java
// Admin.java — Inherits from User
public class Admin extends User {
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password, "ADMIN"); // Calls User constructor
    }
}

// Student.java — Also inherits from User
public class Student extends User {
    public Student(int id, String name, String email, String password) {
        super(id, name, email, password, "STUDENT"); // Calls User constructor
    }
}
```
> 📌 Both `Admin` and `Student` **inherit** all properties from `User` — avoiding code duplication.

---

### 3️⃣ Encapsulation
```java
// Question.java — All fields are private, accessed via getters/setters
public class Question {
    private int id;              // ← private
    private String subject;      // ← private
    private String questionText; // ← private
    private char correctOption;  // ← private

    // Controlled access via public getter
    public String getQuestionText() { return questionText; }
    public char getCorrectOption()  { return correctOption; }
}
```
> 📌 No one can directly access or modify fields — they must go through the getter/setter methods.

---

### 4️⃣ Polymorphism
```java
// Same method name — different behaviour for Admin and Student
Admin admin = new Admin(...);
admin.showDashboard();
// OUTPUT: Shows Admin menu (Create Exam, View Results...)

Student student = new Student(...);
student.showDashboard();
// OUTPUT: Shows Student menu (View Exams, My Results...)
```
> 📌 Same `showDashboard()` method behaves **differently** based on the object type.

---

### 5️⃣ Interfaces
```java
// Authenticable.java — defines a contract
public interface Authenticable {
    boolean login(String email, String password);
    void logout();
}

// AuthService.java — implements the contract
public class AuthService implements Authenticable {
    @Override
    public boolean login(String email, String password) { ... }

    @Override
    public void logout() { ... }
}
```
> 📌 Interfaces define **what** must be done — classes decide **how** to do it.

---

## 🗄️ Database Schema

```sql
┌─────────────────────────────────────────────────────────┐
│                    DATABASE: exam_portal                │
└─────────────────────────────────────────────────────────┘

┌──────────────────┐      ┌──────────────────┐
│      USERS       │      │      EXAMS        │
├──────────────────┤      ├──────────────────┤
│ id (PK)          │      │ id (PK)          │
│ name             │      │ title            │
│ email (UNIQUE)   │      │ subject          │
│ password         │      │ duration         │
│ role             │      │ question_count   │
└──────────────────┘      │ created_by (FK)  │
                          └──────────────────┘
┌──────────────────┐      ┌──────────────────┐
│    QUESTIONS     │      │     RESULTS      │
├──────────────────┤      ├──────────────────┤
│ id (PK)          │      │ id (PK)          │
│ subject          │      │ student_id (FK)  │
│ question_text    │      │ exam_id (FK)     │
│ option_a         │      │ score            │
│ option_b         │      │ total            │
│ option_c         │      │ percentage       │
│ option_d         │      │ grade            │
│ correct_option   │      │ submitted_at     │
└──────────────────┘      └──────────────────┘
```

---

## 📦 Installation

### Prerequisites

Make sure you have the following installed:

```
✅ Java JDK 21 or higher     → https://www.oracle.com/java/technologies/downloads/
✅ Apache Maven 3.9+         → https://maven.apache.org/download.cgi
✅ MySQL 8.0+                → https://dev.mysql.com/downloads/installer/
✅ MySQL Workbench            → https://dev.mysql.com/downloads/workbench/
✅ VS Code                   → https://code.visualstudio.com/
✅ Git                       → https://git-scm.com/downloads
```

### Verify Installation

```bash
java -version    # Should show Java 21
mvn -version     # Should show Maven 3.9+
mysql --version  # Should show MySQL 8.0+
git --version    # Should show git version
```

---

## 🚀 How to Run

### Step 1 — Clone the Repository
```bash
git clone https://github.com/YourUsername/OnlineExamSystem.git
cd OnlineExamSystem
```

### Step 2 — Set Up Database
Open **MySQL Workbench** and run:
```sql
CREATE DATABASE exam_portal;
USE exam_portal;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    role VARCHAR(20)
);

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    subject VARCHAR(50),
    question_text VARCHAR(500),
    option_a VARCHAR(200),
    option_b VARCHAR(200),
    option_c VARCHAR(200),
    option_d VARCHAR(200),
    correct_option CHAR(1)
);

CREATE TABLE exams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    subject VARCHAR(50),
    duration INT,
    question_count INT,
    created_by INT
);

CREATE TABLE results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    exam_id INT,
    score INT,
    total INT,
    percentage DOUBLE,
    grade VARCHAR(20),
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Default users
INSERT INTO users (name, email, password, role) VALUES
('Admin', 'admin@exam.com', 'admin123', 'ADMIN'),
('Priya Sharma', 'priya@exam.com', 'priya123', 'STUDENT'),
('Arjun Mehta', 'arjun@exam.com', 'arjun123', 'STUDENT');

-- Sample questions
INSERT INTO questions VALUES
(NULL,'Science','Which planet is closest to the Sun?','Mercury','Venus','Earth','Mars','A'),
(NULL,'Science','What is the chemical symbol for water?','H2O','CO2','NaCl','O2','A'),
(NULL,'Math','What is square root of 144?','11','12','13','14','B'),
(NULL,'Math','If 3x = 21, what is x?','5','6','7','8','C'),
(NULL,'History','Who was first President of USA?','Abraham Lincoln','Thomas Jefferson','George Washington','John Adams','C'),
(NULL,'Computer','What does CPU stand for?','Central Processing Unit','Computer Processing Unit','Core Power Unit','Control Processing Unit','A');

-- Sample exams
INSERT INTO exams VALUES
(NULL,'General Knowledge Quiz','Mixed',8,6,1),
(NULL,'Science & Math Test','Mixed',6,5,1),
(NULL,'Computer Basics','Computer',5,4,1);
```

### Step 3 — Configure Database Connection
Copy the example properties file:
```bash
copy src\main\resources\application.properties.example src\main\resources\application.properties
```

Open `application.properties` and update:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/exam_portal
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 4 — Build the Project
```bash
mvn clean install -DskipTests
```

### Step 5 — Run the Server
```bash
mvn spring-boot:run
```

You should see:
```
==========================================
   ExamPortal Server Started!
   Running at: http://localhost:8080
==========================================
```

### Step 6 — Open in Browser
```
http://localhost:8080
```

---

## 🌐 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/auth/register` | Register new user |
| `POST` | `/api/auth/login` | Login user |
| `GET` | `/api/exams` | Get all exams |
| `POST` | `/api/exams` | Create new exam |
| `DELETE` | `/api/exams/{id}` | Delete exam |
| `GET` | `/api/questions` | Get all questions |
| `GET` | `/api/results` | Get all results (Admin) |
| `GET` | `/api/results/{studentId}` | Get student results |
| `POST` | `/api/results` | Save exam result |
| `GET` | `/api/students` | Get all students |

### Example API Request — Register
```json
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "name": "Rahul Kumar",
  "email": "rahul@example.com",
  "password": "rahul123",
  "role": "STUDENT"
}
```

### Example API Response
```json
{
  "success": true,
  "message": "Registration successful! You can now login."
}
```

---

## 🔐 Default Credentials

| Role | Email | Password |
|------|-------|----------|
| 👨‍💼 Admin | admin@exam.com | admin123 |
| 🎓 Student | priya@exam.com | priya123 |
| 🎓 Student | arjun@exam.com | arjun123 |

> **Note:** You can also register your own account through the website.

---

## 📚 Subjects in Question Bank

| Subject | Questions |
|---------|-----------|
| 🔬 Science | 4 questions |
| 📐 Math | 4 questions |
| 📜 History | 4 questions |
| 🌍 Geography | 4 questions |
| 📖 English | 4 questions |
| 💻 Computer | 4 questions |
| **Total** | **24 questions** |

---

## 🎨 Pages Overview

| Page | URL | Description |
|------|-----|-------------|
| 🏠 Home | `/` | Landing page with features |
| 🔐 Login | `/login.html` | Sign in page |
| 📝 Register | `/register.html` | Create account |
| 🎓 Student | `/student.html` | Student dashboard |
| 👨‍💼 Admin | `/admin.html` | Admin control panel |
| ⏱️ Exam | `/exam.html` | Live exam engine |
| 📊 Result | `/result.html` | Result and review |

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
```bash
git checkout -b feature/YourFeature
```
3. Commit your changes
```bash
git commit -m "Add YourFeature"
```
4. Push to the branch
```bash
git push origin feature/YourFeature
```
5. Open a Pull Request

---

## 👨‍💻 Developer

**Tapan** — Computer Science Student

> Built with ❤️ using Java, Spring Boot, MySQL, HTML, CSS, and JavaScript

---

## 📄 License

This project is built for educational purposes as part of a college assignment demonstrating Object-Oriented Programming concepts in Java.

---

<div align="center">

**⭐ Star this repository if you found it helpful!**

![Java](https://img.shields.io/badge/Made_with-Java-orange?style=flat-square&logo=java)
![Spring](https://img.shields.io/badge/Powered_by-Spring_Boot-green?style=flat-square&logo=springboot)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue?style=flat-square&logo=mysql)

</div>
