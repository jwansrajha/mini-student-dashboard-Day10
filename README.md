# Student Dashboard – Day 10

## Project Overview

A simple full-stack Student Dashboard developed as part of Day 10 of my internship training.

The project demonstrates how Angular, Spring Boot, and PostgreSQL work together using REST APIs.

Users can view a list of students and add new students through the web interface.

## Technologies Used

- **Frontend:** Angular, TypeScript, HTML, CSS
- **Backend:** Java, Spring Boot
- **Database:** PostgreSQL
- **API:** REST API (HTTP GET and POST)
- **Tools:** VS Code, Maven, npm, pgAdmin

## Project Structure

```text
mini-student-dashboard/
├── student-frontend/    # Angular application
├── student-backend/     # Spring Boot application
└── README.md
```

## Features

- Display students and their grades.
- Add new students.
- Fetch data using GET requests.
- Send student data using POST requests.
- Store student information in PostgreSQL.
- Connect the frontend and backend through REST APIs.

## How It Works

1. The user interacts with the Angular frontend.
2. Angular sends an HTTP request to the Spring Boot backend.
3. Spring Boot processes the request and communicates with PostgreSQL.
4. PostgreSQL stores or retrieves student data.
5. Spring Boot returns an HTTP response.
6. Angular displays the result to the user.

**Request Flow:**

Angular → Spring Boot → PostgreSQL → Spring Boot → Angular

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/students` | Retrieve all students |
| POST | `/api/students` | Add a new student |

### Example POST Request

```json
{
  "name": "Jwan",
  "grade": 95
}
```

## How to Run

### 1. Database Setup

Install PostgreSQL and create a database named:

```sql
CREATE DATABASE student_demo;
```

Configure the backend's database connection using the appropriate environment variables, including `DB_USER` and `DB_PASSWORD`.

### 2. Run the Backend

Open a terminal in the `student-backend` directory:

```bash
cd student-backend
./mvnw spring-boot:run
```

Backend URL:

`http://localhost:8080`

Test the API:

`http://localhost:8080/api/students`

### 3. Run the Frontend

Open another terminal:

```bash
cd student-frontend
npm install
npm start
```

Frontend URL:

`http://localhost:4200`

## Learning Outcomes

During Day 10, I learned and practiced:

- Understanding the frontend–backend–database flow.
- Sending and receiving HTTP requests and responses.
- Using GET and POST methods.
- Working with JSON data.
- Connecting Angular to Spring Boot REST APIs.
- Understanding how PostgreSQL stores application data.
- Testing the complete application integration.

## Author

**Jwan Salah Rajha**

Computer Science Student – Birzeit University

Internship Training – Asal Technologies
