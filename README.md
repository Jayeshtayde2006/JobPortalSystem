# 💼 Job Portal System

> A desktop-based recruitment management application built using Java Swing, JDBC, and MySQL.

The **Job Portal System** is a Java-based desktop application designed to streamline the recruitment process by connecting **Candidates, Employers, and Administrators** through a centralized platform.

The system provides role-based access, job management, candidate applications, profile management, application tracking, and administrative controls.



## 📌 Overview

The application follows a modular architecture with separate modules for:

- 👤 **Candidates** – Search and apply for jobs
- 🏢 **Employers** – Post and manage job opportunities
- 🛡️ **Administrators** – Manage users, jobs, and applications

The project demonstrates practical implementation of **Core Java, Object-Oriented Programming, GUI development, database connectivity, SQL, authentication, and basic application security**.



## ✨ Key Features

### 👤 Candidate Module

- Candidate registration and login
- Secure password authentication
- Candidate profile management
- Add phone, education, skills, and experience
- Browse available jobs
- Search jobs by keyword
- Filter jobs by location and job type
- View complete job details
- Apply for jobs
- Prevent duplicate applications
- View submitted applications
- Track application status

### 🏢 Employer Module

- Employer registration and login
- Employer dashboard
- Post new job vacancies
- View posted jobs
- Edit existing job postings
- Delete job postings
- View applications received
- Update candidate application status

### 🛡️ Admin Module

- Administrator authentication
- Dashboard statistics
- View all registered users
- View all job postings
- View all applications
- Delete users
- Delete jobs
- Centralized system management



## 🔐 Security Features

Security has been considered during application development.

- Passwords are protected using **PBKDF2WithHmacSHA256**
- Password hashing uses unique salts
- Database operations use **PreparedStatement**
- SQL injection risk is reduced through parameterized queries
- Employer authorization is checked before modifying jobs
- Employers can update applications only for their own jobs
- Duplicate job applications are prevented
- Database credentials are excluded from version control

---

## 🛠️ Technology Stack

| Technology | Usage |
|---|---|
| **Java** | Application development |
| **Java Swing** | Desktop GUI |
| **JDBC** | Database connectivity |
| **MySQL** | Relational database |
| **SQL** | Database operations |
| **Eclipse IDE** | Development |
| **Git** | Version control |
| **GitHub** | Source code management |



## 🏗️ Architecture

The project follows a layered structure:

text
┌──────────────────────────────┐
│        Swing UI Layer        │
│ Login / Register / Dashboards│
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│        DAO Layer             │
│ UserDAO / JobDAO /           │
│ ApplicationDAO               │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│       JDBC Connection        │
│        DBConnection          │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│        MySQL Database        │
│ Users / Jobs / Applications  │
└──────────────────────────────┘

🗄️ Database Design
Database
job_portal
Main Tables
users

Stores candidate, employer, and administrator information.

id
name
email
password
role
phone
education
skills
experience
jobs

Stores job vacancy information.

id
employer_id
job_title
company_name
location
salary
job_type
description
required_skills
applications

Stores candidate job applications.

id
job_id
candidate_id
application_date
status
Relationships
Users
  │
  ├──────────< Jobs
  │             │
  │             │
  └──────────< Applications >──────── Jobs
📂 Project Structure
JobPortalSystem/
│
├── README.md
├── .gitignore
│
└── src/
    │
    ├── module-info.java
    │
    └── com/
        └── jobportal/
            │
            ├── dao/
            │   ├── ApplicationDAO.java
            │   ├── JobDAO.java
            │   ├── PasswordUtil.java
            │   └── UserDAO.java
            │
            ├── db/
            │   └── DBConnection.java
            │
            ├── main/
            │   └── JobPortalApp.java
            │
            ├── model/
            │   ├── Application.java
            │   ├── Job.java
            │   └── User.java
            │
            └── ui/
                ├── AdminApplicationsFrame.java
                ├── AdminDashboard.java
                ├── AdminDeleteFrame.java
                ├── CandidateDashboard.java
                ├── EditJobFrame.java
                ├── EmployerDashboard.java
                ├── LoginFrame.java
                ├── MyApplicationsFrame.java
                ├── MyJobsFrame.java
                ├── PostJobFrame.java
                ├── ProfileFrame.java
                ├── RegisterFrame.java
                ├── ViewAllJobsFrame.java
                ├── ViewApplicationsFrame.java
                ├── ViewJobsFrame.java
                └── ViewUsersFrame.java
⚙️ Installation & Setup
Prerequisites

Make sure the following are installed:

Java JDK 21 or compatible version
Eclipse IDE
MySQL Server
MySQL Connector/J
Git
1. Clone the Repository
git clone https://github.com/Jayeshtayde2006/JobPortalSystem.git
2. Open in Eclipse

Import the project into Eclipse as a Java project.

3. Create the Database

Create the MySQL database:

CREATE DATABASE job_portal;
USE job_portal;

Create the required users, jobs, and applications tables.

4. Configure Database Connection

Open:

src/com/jobportal/db/DBConnection.java

Configure the MySQL username and password for your local environment.

Do not commit real database passwords or credentials to GitHub.

5. Add MySQL Connector/J

Add the MySQL Connector/J .jar file to the project's build path.

6. Run the Application

Run:

JobPortalApp.java

The application will launch with the login screen.

🔄 Application Workflow
                 Job Portal System
                        │
          ┌─────────────┼─────────────┐
          ▼             ▼             ▼
      Candidate      Employer       Admin
          │             │             │
          ▼             ▼             ▼
      Search Jobs    Post Jobs     Manage Users
          │             │             │
          ▼             ▼             ▼
      Apply Job     Manage Jobs    Manage Jobs
          │             │             │
          ▼             ▼             ▼
   Track Application  View Apps   View Applications
🧪 Testing

The application was tested for major functional and security scenarios including:

User registration
User login
Password verification
Candidate profile updates
Job creation
Job editing
Job deletion
Job searching and filtering
Job application
Duplicate application prevention
Application status updates
Employer authorization
Admin operations
Database consistency

🚀 Future Enhancements

Possible future improvements include:

Resume upload and management
Email notifications
Job recommendation system
Advanced search and filtering
Interview scheduling
Recruiter-candidate messaging
Application analytics
Web version using Spring Boot
REST API integration
Cloud database deployment

🎯 Learning Outcomes

This project provided practical experience in:

Object-Oriented Programming
Core Java
Java Swing GUI development
JDBC
MySQL and SQL
DAO architecture
Authentication and password hashing
CRUD operations
Role-based access control
Input validation
Git and GitHub
Basic application security
